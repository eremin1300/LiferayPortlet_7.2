package myapp.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import myapp.models.UserModel;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Света и Витя
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Portlet",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=myapp_Portlet_MVC",
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",

        },
        service = Portlet.class
)
public class PortletMVC extends MVCPortlet  {
    private boolean resultFlag = false;     //оба флага - для сохранения результата поиска
    private boolean searchFlag = false;
    @Reference
    private volatile UserLocalService userLocalService;

    public static ArrayList<UserModel> getUserModels() {
        return UserModels;
    }

    static ArrayList<UserModel> UserModels = new ArrayList<>();

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse)
            throws PortletException, IOException {
        String str = renderRequest.getRemoteUser();

        if(!resultFlag && !searchFlag) {
            if (str != null && !str.isEmpty() && !str.equals("")) {
                try {
                    User user = userLocalService.getUserById(Long.decode(str));
                    writeUsersList(user);
                } catch (PortalException e) {
                    e.printStackTrace();
                }
            } else UserModels.clear();
        }
        else resultFlag = false;
        if (UserModels != null && UserModels.size() > 0) {
            renderRequest.setAttribute("users", UserModels);
        }
        super.render(renderRequest, renderResponse);
    }

    private static final Log log = LogFactoryUtil.getLog(
            PortletMVC.class);

    //Можно вынести данный метод в отдельный класс, но согласно документации liferay
    // если проект небольшой то можно все оставить в основном классе =)

    private void writeUsersList(User currentUser) {
        ArrayList<UserModel> result = new ArrayList<>();
        for (User user : userLocalService.getUsers(-1, -1)) {
            if (!user.isDefaultUser()) {
                try {
                    ArrayList<String> phones = new ArrayList<>();
                    ArrayList<String> roles = new ArrayList<>();
                    ArrayList<String> organizations = new ArrayList<>();

                    if (user.getPhones().size() > 0 && user.getPhones() != null) {
                        for (Phone phone : user.getPhones()) {
                            phones.add(phone.getNumber());
                        }
                    } else phones.add("no phones");

                    for (Role role : user.getRoles()) {
                        roles.add(role.getName());
                    }

                    if (user.getOrganizations().size() > 0 && user.getOrganizations() != null) {
                        for (Organization organization : user.getOrganizations()) {
                            organizations.add(organization.getName());
                        }
                    } else organizations.add("no organizations");

                    result.add(new UserModel(
                            user.getUserId(), user.getFullName(), user.getEmailAddress(),
                            user.getJobTitle(), user.getBirthday(), phones, organizations, roles
                    ));
                } catch (PortalException e) {
                    log.debug(e.getMessage());
                }
            }
        }
        UserModels.clear();
        ArrayList<String> rolesofCurrentUser = new ArrayList<>();
        for (Role role : currentUser.getRoles()) {
            rolesofCurrentUser.add(role.getName());
        }
        if (rolesofCurrentUser.contains("Administrator"))
            UserModels.addAll(result);
        else {
            for (UserModel user : result) {
                if (rolesofCurrentUser.containsAll(user.getRoles()))
                    UserModels.add(user);
            }
        }
    }

    public static ArrayList<UserModel> getUserModels(int start, int end) {
        ArrayList<UserModel> userModel = new ArrayList<UserModel>();
        for (int i = start; i < end && i < UserModels.size(); i++) {
            userModel.add(UserModels.get(i));
        }
        return userModel;
    }

    public void findByKeyword(ActionRequest actionRequest, ActionResponse actionResponse)
            throws PortletModeException, SQLException, PortalException {
        ArrayList<UserModel> UserModelsTemp = new ArrayList<>();
        String keyword = ParamUtil.getString(actionRequest, "keyword");
        String str = actionRequest.getRemoteUser();
        if (keyword != null && keyword != "") {
            writeUsersList(userLocalService.getUserById(Long.decode(str)));
            for (UserModel usr: UserModels) {
                if (usr.getFullName().contains(keyword))
                UserModelsTemp.add(usr);
            }
            UserModels.clear();
            UserModels.addAll(UserModelsTemp);
            resultFlag = true;
            searchFlag = true;
            actionResponse.setPortletMode(PortletMode.VIEW);
        } else {
            searchFlag = false;
            writeUsersList(userLocalService.getUserById(Long.decode(str)));
            actionResponse.setPortletMode(PortletMode.VIEW);
        }
    }
}