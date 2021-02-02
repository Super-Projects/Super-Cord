![alt text](https://avatars.githubusercontent.com/u/78378240?s=200&v=4)
# Plugin Setup
Here is the best and easiest way to set up the plugin. If you follow all of them, you should have no problems with the setup.

> Please note that this plugin necessarily requires a MySQL database connection. If this is not provided, the plugin will not work.

###### MySQL
Setting up the plugin is very simple. All that is needed is a MySQL database.

The plugin should have its own database to guarantee a secure process and use. How to create a database under php myadmin you can read here. You don't need to create tables, the plugin does that for you.


###### Steps
1. Copy the plugin into the `plugins` folder of your proxy
2. Start your BungeeCord- Server
3. The server will stop again because a MySQL.yml file had to be created first. Enter your access data to the database under `plugins/Super-Cord/mysql.yml`.
4. Now give your player account the permission `supercord.admin` in the `config.yml` file of the proxy.
5. Start your proxy again.
6. When you now enter the server, you will be notified that you have been registered as an admin. You can now start setting up all the other modules.
