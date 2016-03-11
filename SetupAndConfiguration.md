### Before you start ###

  1. Sign up for an App Engine account (free) and create an App Engine application for hosting GDBE.
  1. Download and install <a href='http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/galileo/SR1/eclipse-jee-galileo-SR1-win32.zip'>Eclipse</a>, along with the <a href='http://code.google.com/eclipse/'>Google Plugin</a>.
  1. Download and extract the latest version of GDBE from the <a href='http://code.google.com/p/gdbe/downloads/list'>downloads section</a>.

### Opening GDBE in Eclipse ###
  1. In Eclipse, from the **File** menu, click **Import**. From the Import window select **General** > **Existing Projects into Workspace** and click **Next**. <br /><br /><img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/ImportExistingProject.png' /><br /><br />
  1. Select the GDBE project folder that was downloaded and extracted. Click **Finish**.<br /><br /><img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/ImportExistingProject-Select.png' /><br /><br />

### Configuring the GDBE Project in Eclipse ###
  1. From the Package Explorer right-click the **gdocs-base-editor** project and select **Google** > **App Engine Settings...**.
  1. In the App Engine configuration window specify values for **Application ID** and **Version** matching the name and version of the App Engine application into which to deploy GDBE.<br /><br /><img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/ConfigureProject-AppEngine.png' /><br /><br />

### Running GDBE locally in Hosted mode ###
  1. With the **gdocs-base-editor** project selected in the **Package Explorer**, from the **Run** menu select **Run As** > **Web Application**.<br /><br /><img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/RunProject-HostedMode.png' /><br /><br />
  1. In the **HTML Page Selection** window select **gdbe-splash.html** and click **OK**.<br /><br /><img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/RunProject-HostedMode-PageSelection.png' /><br /><br />
  * Note: There's a <a href='http://code.google.com/p/google-web-toolkit/issues/detail?id=2962'>known issue</a> in Ubuntu with GWT Hosted Mode and SSL. Because GDBE uses AuhSub authentication, which involves redirecting the user to an authorization page over SSL, you may experience this issue. You can still run the application locally by opening the hosted mode URL (typically http://localhost:8080) in a separate browser instance, though this will bypass GWT hosted mode.

### Deploying GDBE to the App Engine ###
  1. From the Package Explorer right-click the **gdocs-base-editor** project and select **Google** > **Deploy to App Engine**.
  1. In the deployment window provide your App Engine login details and click **Deploy**.<br /><br /><img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/DeployProject-AppEngine.png' /><br /><br />