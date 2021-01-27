package myapp.models;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;

import java.util.Date;
import java.util.List;

public class UserModel {


    private Long id;
    private String fullName;
    private String eMail;
    private String position;
    private Date birthDate;
    private List<String> phones;
    private List<String> organizations;
    private List<String> roles;

    public UserModel(Long id, String fullName, String eMail, String position, Date birthDate,
                     List<String> phones, List<String> organizations, List<String> roles) {
        this.id = id;
        this.fullName = fullName;
        this.eMail = eMail;
        this.position = position;
        this.birthDate = birthDate;
        this.phones = phones;
        this.organizations = organizations;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UserModel() {
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<String> organizations) {
        this.organizations = organizations;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", position='" + position + '\'' +
                ", birthDate=" + birthDate +
                ", phones=" + phones +
                ", organizations=" + organizations +
                ", roles=" + roles +
                '}';
    }
}
