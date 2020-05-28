package almakov_lab9_tags;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import almakov_lab9_pack.User;
import almakov_lab9_pack.UserExistsException;
import almakov_lab9_pack.UserList;
import almakov_lab9_pack.UserListHelper;

public class AddUser extends SimpleTagSupport {
	private User user;
	public void setUser(User user){
		this.user=user;
	}
	public void doTag() throws JspException, IOException{
		String errorMessage=null;
		UserList userList=(UserList)getJspContext().getAttribute("users",PageContext.APPLICATION_SCOPE);
		if(user.getLogin()==null||user.getLogin().equals("")){
			errorMessage="Login field cannot be empty";
		}else{
			if(user.getName()==null||user.getName().equals("")){
				errorMessage="The name field cannot be empty";
			}
		}
		if(errorMessage==null){
			try {
				userList.addUser(user);
				getJspContext().setAttribute("users", userList);
				UserListHelper.saveUserList(userList);
			} catch (UserExistsException e) {
				errorMessage="Such username has already exist";
			}
			
		}
		getJspContext().setAttribute("errorMessage",errorMessage,PageContext.SESSION_SCOPE);
	}
}
