# Vaccination-Management-System
Database Management Systems Project

Languages: Java and FXML

Database: MySQL

This is a clone of Vaccination Booking website Co-Win (https://selfregistration.cowin.gov.in)

## Features
- Allows user to register and login on the application
- After logging in, a user can register upto 4 members
- Users can search for the nearest vaccination centre by either using pin code or by selecting a district.
- There are 2 types of admins(local and global) local can only change info related to their specific centre.
- Global admins can add new vaccination centres.
- After the vaccination, local admins can mark attendance
- If vaccination is completed then the user can download a PDF certificate.
- Local and global admins can update number of available slots on each day
- Captcha is used to avoid computerized vaccine bookings.
- Simple GUI for users and admins

## ER Diagram
<img src="https://user-images.githubusercontent.com/34760210/127901503-edd53012-5b22-4b5c-b67c-b68cd7d0697b.jpg" alt="ER Diagram" width="600"/>

## Schema Diagram
![image14](https://user-images.githubusercontent.com/34760210/127901520-957306ca-fabc-474a-ab04-dcc2cc05a891.png)

## Data Colection
Vaccination centre database: (2038 Vaccine centres)
Vaccine Information & Govt Approved Vaccine Centres.pdf https://www.uhcpindia.com/web/statenotifications/documents/Networks/Vaccine%20Information%20&%20Govt%20Approved%20Vaccine%20Centres.pdf

COVID FIGHTERS RESOURCES - https://docs.google.com/spreadsheets/u/1/d/1OL7go19rRpSdxemQXHM0cTBds2hjspj7_U7Ag7NdOCQ/htmlview

Captcha images database: (1039 CAPTCHA Images) https://www.kaggle.com/fournierp/captcha-version-2-images

## User Interface
### User Login
![1](https://user-images.githubusercontent.com/34760210/129450215-1ebd381a-fd81-4d10-b706-d8d77e24d92a.JPG)

### All Members
![1](https://user-images.githubusercontent.com/34760210/128496316-b9c2591a-933d-4316-9e42-3f0f0319eee7.JPG)

### Add New Member
![2](https://user-images.githubusercontent.com/34760210/128496396-59e3b178-26d3-418d-8f58-fb7537488cb2.JPG)

### Admin Options
![3](https://user-images.githubusercontent.com/34760210/128496542-1c6554e0-ed2e-4c71-8fd9-5b6790beb0eb.JPG)

### Add New Vaccination Centre
![1](https://user-images.githubusercontent.com/34760210/133609933-9e0f1492-2a10-417f-9276-f185ce34c9c2.JPG)

### Search Vaccination Centre by District or Pin Code
![4](https://user-images.githubusercontent.com/34760210/128496881-c2f82100-f60d-4e93-a81a-a16c353f53b5.JPG)

### FAQ Page
![3](https://user-images.githubusercontent.com/34760210/133610139-29513fd9-6469-4e32-be3e-693338f1b74a.JPG)

### Edit Existing Members
![2](https://user-images.githubusercontent.com/34760210/133610268-0ba855f1-f74f-4f15-a36c-14f0c1e8ff1d.JPG)

### Vaccination centre selection
![5](https://user-images.githubusercontent.com/34760210/128498571-8e672f93-645b-4b3a-b732-18c548cb64b3.JPG)

### Booking Confirmation
![6](https://user-images.githubusercontent.com/34760210/128498659-ef534122-e38a-4a11-9ffe-d31839d82834.JPG)

### Attendance Marking by Admin
![7](https://user-images.githubusercontent.com/34760210/128498980-80489470-b25b-4f19-acee-a638bf2bb2f7.JPG)

### Download Vaccination Certificate
![8](https://user-images.githubusercontent.com/34760210/128499120-5139819f-c78b-4fc7-a018-8bee41949b09.JPG)

### Sample Vaccination Certificate PDF
![94c24bfeb69f4c37a68f181d9ff44dbf-0001](https://user-images.githubusercontent.com/34760210/128499276-074b9ed1-6fb9-4a34-8c2e-726f078b618e.jpg)


## Summary

Our project can help :
- Government
- Hospitals
- Citizens

The project highlights the use of :
- Techniques for system analysis and design, such as data flow diagrams
- Database handling and query processing. 
- Database Designing. 
- User interface design 
- Several features that make the application easier to use

