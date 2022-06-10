# Used keycloak-18.0.0

# Theme Setup
1. Copy 'mytheme' folder to themes folder in keycloak
2. Log into the Admin Console. 
3. Select your realm from the drop-down box in the top left corner. 
4. Click Realm Settings from the menu. 
5. Click the Themes tab.
6. Select 'mytheme' from drop-down box for Login Theme

# Provider Setup
1. Create jar
2. Copy jar to providers folder in keycloak
3. Run bin/kc.bat build
4. Run bin/kc.bat start-dev

# Authentication Setup
1. Log into Admin Console
2. Select your realm from the drop-down box in the top left corner.
3. Click Authentication from the menu.
4. Copy 'Browser' flow
5. Delete existing executions for Browser-v1 Forms
6. Add new execution 'My Username Password Form V2'
7. Configure the new execution to switch 'Take Mobile Number' ON
8. Click Save
9. Go to Bindings tab
10. Set browser-v1 from drop-down box for Browser Flow
