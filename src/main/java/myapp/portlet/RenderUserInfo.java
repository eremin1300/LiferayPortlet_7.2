package myapp.portlet;


import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import myapp.models.UserModel;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Liferay
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=myapp_Portlet_MVC",
                "mvc.command.name=/renderUserInfo/render"
        },
        service = MVCRenderCommand.class
)
public class RenderUserInfo implements MVCRenderCommand {
    static Map<String, String> months = new HashMap<>();
    static{
        months.put("01", "января");
        months.put("02", "февраля");
        months.put("03", "марта");
        months.put("04", "апреля");
        months.put("05", "мая");
        months.put("06", "июня");
        months.put("07", "июля");
        months.put("08", "августа");
        months.put("09", "сентября");
        months.put("10", "октября");
        months.put("11", "ноября");
        months.put("12", "декабря");

    }
    PortletMVC portletMVC;
    ArrayList<UserModel> userModels = new ArrayList<>();

    @Override
    public String render(
            RenderRequest renderRequest, RenderResponse renderResponse)
            throws PortletException {
        String userId = ParamUtil.getString(renderRequest, "userId");
        userModels = portletMVC.getUserModels();
        UserModel requestedUser = getUserById(userId);
        DateFormat df = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);
        String date = df.format(requestedUser.getBirthDate()) + " г.";
        String birthDate = new String();
        for (String key : months.keySet()) {
            if (date.substring(3,5).equals(key))
                birthDate = date.substring(1,3)  + months.get(key) + " "+ date.substring(6);
        }
        renderRequest.setAttribute("requestedUser", requestedUser);
        renderRequest.setAttribute("birthDate", birthDate);
        return "/viewUserDetails.jsp";
    }

    private UserModel getUserById(String id){
        for (UserModel user : userModels) {
            if (user.getId().equals(Long.decode(id)))
                return user;
        }
        return null; //знаю что нельзя возвращать null, но в данном случае это будет скорее исключение чем правило
    }


}