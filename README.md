[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/VBB5K7qa)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=12084639&assignment_repo_type=AssignmentRepo)

Welcome to Gong Cha's Teahouse POS system. TO run the application, you will need the following:

1. PostgreSQL database installed on your system
2. Amazon Corretto 20 JDK installed on your system
3. Maven installed on your system

## Setting up Postgres
Note: I'm unsure if we have to have the person using this application set up postgres, as I think we are hosting it? I'm not sure. I'll leave this here for now.

The main idea is to do the following:
- Create a database "teahouse"
- Create schema *insert schemas*  in "teahouse"
- Create tables *insert tables*

The steps to acomplish this are as follows:
#### To create the tables, run the following commands in the terminal:
- First, connect to the database and run the script using the following command:
- If you are hosting a local database, do the following (once navigated to the proper folder containing the .sql file):
    - psql -U USER -d **DBNAME** -f .\master_delete_create_insert.sql
        *Typically, User defaults to postgres. DB name is the name of the database you want to connect to. The -f flag specifies the file to run.*

### If you are hosting a remote database, such as the TAMU one, do the following:

First, connect to the database using the following command:
psql -h csce-315-db.engr.tamu.edu -U **csce315_909_NETID** -d **DATABASENAME**

In our case, it is the following: psql -h csce-315-db.engr.tamu.edu -U csce315_909_**netid** -d csce315331_09b_db

Then, run the script using the following command:
\i path/to/file/master_delete_create_insert.sql



