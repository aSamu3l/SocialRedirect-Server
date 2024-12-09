# SocialRedirect-Server

This project aims to allow easy redirects to our social networks and to manage them through the SERVER, easily changing the destination.

## Redirects

The Redirects service allows redirecting to social accounts using the subdomain of the link from which it is called. For example, a link like `https://alias.mydomain.com` will redirect to the social account associated with the alias.

### Requirements

- Linux Machine
- Java 17
- Maven
- MariaDB
- Apache2
- Certbot (Optional)
- Domain with custom subdomains

### Installation

1. Clone the repository
```shell
git clone https://github.com/aSamu3l/SocialRedirectAndServer.git
cd SocialRedirectAndServer/Redirects
```
2. Run the following command to build the project
```shell
mvn package # or mvn clean package
cp target/Redirect-0.0.1-SNAPSHOT.jar . # Copy the jar to other folder
# copy the application.properties to the same folder and modify it with the correct values
```
3. Create in the database the table `redirectdb` or with the name specified in the application.properties, the table will be created automatically
4. Create a new service file in `/etc/systemd/system/redirects.service` with the following content
```shell
[Unit]
Description=Redirects Service

[Service]
User=root # Change to the user that will run the service
WorkingDirectory=/path/to/Redirects
ExecStart=java -jar Redirect-0.0.1-SNAPSHOT.jar # --spring.config.location=file:./application.properties
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```
5. Enable and start the service
```shell
systemctl enable redirects
systemctl start redirects
```
6. Configure Apache2 to redirect the subdomains to the service
```shell
# Create a new file in /etc/apache2/sites-available/redirects.conf
<VirtualHost *:80>
    ServerName gh.yourdomain.com
    ServerAlias github.yourdomain.com
    # And other aliases or *.yourdomain.com to redirect all subdomains

    # Log
    ErrorLog ${APACHE_LOG_DIR}/socialr-error.log
    CustomLog ${APACHE_LOG_DIR}/socialr-access.log combined

    # Reverse Proxy
    ProxyPass / http://localhost:<port>/ # Change the port with the one specified in the application.properties
    ProxyPassReverse / http://localhost:<port>/ # Change the port with the one specified in the application.properties

    # Pass headers to the service
    <Location />
        ProxyPreserveHost On
        RequestHeader set X-Forwarded-Host "%{HTTP_HOST}s"
        RequestHeader set X-Forwarded-For "%{REMOTE_ADDR}s"
        RequestHeader set X-Forwarded-Proto "http"
    </Location>
</VirtualHost>
```
7. Enable the site and restart Apache2
```shell
a2ensite redirects
systemctl restart apache2
```
8. Add subdomain in your DNS configuration
9. (Optional) Configure Certbot to get a certificate for the domain
```shell
certbot --apache -d gh.yourdomain.com -d github.yourdomain.com
```

## Server

The SERVER handles managing, saving, and modifying redirects to social networks. Through an admin interface, it is possible to add new aliases, modify existing ones, or delete them.

### Requirements

- Linux Machine
- Java 17
- Maven
- MariaDB
- Apache2
- Certbot (Optional)
- Domain with custom subdomains
- Redirects service

### Installation

1. Clone the repository
```shell
git clone https://github.com/aSamu3l/SocialRedirectAndServer.git
cd SocialRedirectAndServer/Server
```
2. Run the following command to build the project
```shell
mvn package # or mvn clean package
cp target/Server-0.0.1-SNAPSHOT.jar . # Copy the jar to other folder
# copy the application.properties to the same folder and modify it with the correct values
```
3. Create a new service file in `/etc/systemd/system/server.service` with the following content
```shell
[Unit]
Description=Server Service

[Service]
User=root # Change to the user that will run the service
WorkingDirectory=/path/to/Server
ExecStart=java -jar Server-0.0.1-SNAPSHOT.jar # --spring.config.location=file:./application.properties
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```
4. Enable and start the service
```shell
systemctl enable server
systemctl start server
```
5. Configure Apache2 to redirect the subdomains to the service
```shell
# Create a new file in /etc/apache2/sites-available/server.conf
<VirtualHost *:80>
    ServerName socialconsole.yourdomain.com

    # Log
    ErrorLog ${APACHE_LOG_DIR}/socials-error.log
    CustomLog ${APACHE_LOG_DIR}/socials-access.log combined

    # Reverse Proxy
    ProxyPass / http://localhost:<port>/ # Change the port with the one specified in the application.properties
    ProxyPassReverse / http://localhost:<port>/ # Change the port with the one specified in the application.properties
</VirtualHost>
```
6. Enable the site and restart Apache2
```shell
a2ensite server
systemctl restart apache2
```
7. (Optional) Configure Certbot to get a certificate for the domain
```shell
certbot --apache -d socialconsole.yourdomain.com
```