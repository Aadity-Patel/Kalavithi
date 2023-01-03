#1.1.Environment variable setup

Steps:

1.On the Windows taskbar, right-click the Windows icon and select System.<br/>

2.In the Settings window, under Related Settings, click Advanced system settings.<br/>

3.On the Advanced tab, click Environment Variables.<br/>

4.Click New to create a new environment variable. Click Edit to modify an existing environment variable.<br/>

Variables need to be created for the project:

DB_HOST<br/>
DB_NAME<br/>
DB_PASSWORD<br/>
DB_PORT<br/>
DB_SCHEMA<br/>
DB_USERNAME<br/>

Assign values to the variables as per your postgres configuration.<br/>
5. After creating or modifying the environment variable, click Apply and then OK to have the change take effect.<br/>

#1.2.CORS Setup
Add this line to CORSConfig.java in the addCorsMappings function  
```allowedOrigins("http://localhost:{PORT}")```

