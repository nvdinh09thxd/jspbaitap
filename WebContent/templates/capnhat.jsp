<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.bean.Users" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>CPANEL | VinaEnter Edu</title>  
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="" />
	<link href="<%=request.getContextPath() %>/templates/css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
	<!-- login starts here -->
	<div class="login agile">
		<div class="w3agile-border">
			<h2>TRANG QUẢN TRỊ VIÊN | CẬP NHẬT THÔNG TIN NGƯỜI DÙNG</h2>
			<div class="login-main login-agileits"> 
			<p style='color: red; background-color: yellow; font-weight: bold; font-style: italic'>${err}</p>
		<%
			String username = "";
			String fullname = "";
			if(session.getAttribute("user")!=null){
				Users user = (Users) session.getAttribute("user");
				username = user.getEmail();
				fullname = user.getFullName();
			} else {
				response.sendRedirect(request.getContextPath() + "/login");
			}
			if(request.getParameter("username")!=null){
				username = (String) request.getParameter("username");
			}
			if(request.getParameter("fullname")!=null){
				fullname = (String) request.getParameter("fullname");
			}
		%>
				<div class="userinfo" style="margin-bottom:20px;">
					<p class="fullname">Cập nhật thông tin cho : <%out.print(fullname);%></p>
				</div>
				
				<form method="post"	enctype="multipart/form-data">
					<table class="update-user" border="1" width="500px" align="center"> 
						<tr align="center"> 
							<th>Email</th>
							<td>
								<input type="text" value="<%out.print(username);%>" name="username" class="input-u" />
							</td>						
						</tr>
						
						<tr  align="center"> 
							<th>Full name</th>
							<td>
								<input type="text" value="<%out.print(fullname);%>" name="fullname" class="input-u"/>
							</td>
						</tr>
						
						<tr  align="center"> 
							<th>Avatar</th>
							<td>
								<input type="file" value="Upload file" name="avatar" class="input-u" />
							</td>
						</tr>
						
						<tr  align="center"> 
							<td>&nbsp;</td>
							<td><button> Cập nhật thông tin</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div> 
	</div>
</body>
</html>