# Getting Started
This project contains a simple rest api to handle upload files in the form of excel. It has several features including:
- Upload files
- Process files
- Save files to database and storage
- Review files
- Record all user activities

The technology used for this project is:
- Spring boot version 3
- MySQL
- Docker

## How to Run This Project
I assume that You already installed maven and JDK 17, maven, docker, docker-compose on your environment.
1. Clone the repository: `git clone git@github.com:fahmikudo/file-upload-services.git`
2. Go to the folder: `cd file-upload-services`
3. Create folder for volume docker: `mkdir mysql_data`
4. Build project: `mvn clean install`
5. Run the project using docker compose: `docker-compose up -d --build`
6. Open your browser, hen go to `http://localhost:8080/swagger-ui.html`

## Credentials User Test
| #   | Username | Password |
| --- |:--------:| --------:|
| 1   | testadmin    | s3cr3t     |
| 2   | testuser   | s3cr3t     |

## API Documentation using Swagger
