# Cinema
## A cinema management application with a ticketing system

The new user registers within the application and verifies their email by clicking the activation link received in their email.
Then, the user selects a screening date and a film for that specific time, checking seat availability for the screening. 

In the next step, the user books a ticket, selects a seat, row and chooses the type of discount.
After a successful booking, the application sends an email with the ticket in PDF format with all the details and a unique QR code to confirm the details and the purchase of the ticket.

The cinema offers two types of discounts: 

- Events: Cheap Tuesday and Cheap Friday, during which ticket prices are discounted by 20%.

- Student discounts: with an additional 10% reduction in ticket prices.

The application is connected to the API National Bank of Poland and saves data to a database with the current values of 34 currencies.
The customer can choose from 34 currencies for the ticket price.
A request is sent to the API NBP every 24 hours to have the latest currency values in the database using Scheduler.

Administrators have the authority to add new films to the database and create new screenings, specifying the date, time, and film. 

I used a MySQL database to implement the relationships in the database.
The application is deployed on Docker and has an implemented swagger.


 ## Application is developed using following technologies:
 Core:
<p align="left"><a href="https://www.java.com" target="_blank" rel="noreferrer"> 
<img src="https://ultimateqa.com/wp-content/uploads/2020/12/Java-logo-icon-1.png" alt="java" width="80" height="50"/> 
</a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://e4developer.com/wp-content/uploads/2018/01/spring-boot.png" alt="spring" width="90" height="50"/> 
<a href="https://www.mongodb.com/" target="_blank" rel="noreferrer"> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="50" height="50"/>
 <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="50" height="50"/> </a> 
 <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a>
 <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://mapstruct.org/images/mapstruct.png" alt="docker" width="80" height="50"/></a>
 </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://junit.org/junit4/images/junit5-banner.png" alt="java" width="90" height="50"/>
 <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://javadoc.io/static/org.mockito/mockito-core/1.9.5/org/mockito/logo.jpg" alt="java" width="90" height="50"/></a> </p>
 
 ## To run the application, follow these steps :
- Install IntelliJ IDEA and Docker Desktop on your computer.
- Run Docker Desktop.
- Clone the repository in IntelliJ IDEA using the link https://github.com/Gimi818/Cinema
- Run docker-compose up in the terminal. 
- Run the applications in IntelliJ IDEA.
- Try the applications in Postaman, the steps on how to do it are below.
 
 ## How to use application in Postman:
 
    Step 1 :
    POST localhost:8080/users/registration
    Enter your data.
    JSON:
    {
     "firstName":"Wojciech",
     "lastName":"Gmiterek",
     "email":"cinemaemailtest@gmail.com",
     "password":"password",
    "repeatedPassword" :"password",
    "role":"USER"
    }
  
  <img src="https://github.com/Gimi818/cinema/blob/master/steps/1.0_registration.PNG" width="500" heigt="700"/>

    Step 2 :
    Click on the account activation link.

  <img src="https://github.com/Gimi818/cinema/blob/master/steps/1.1_email.PNG" width="500" heigt="700"/>
  
    Step 3 :
    GET localhost:8080/screenings?date=2023-11-25
    Choose the date you want to go to the cinema type it into the URL and find a film you like.
    (screenings are available from 2023-11-20 to 2023-12-05)
    
   <img src="https://github.com/Gimi818/cinema/blob/master/steps/2.0_Screenings.png" width="500" heigt="700"/>

    Step 4 :
    GET localhost:8080/screenings/1
    Enter the film id in the URL and check available seats for the screening.
    
   <img src="https://github.com/Gimi818/cinema/blob/master/steps/3.0_seats.PNG" width="500" heigt="700"/>

    Step 5 :
    POST localhost:8080/book/1/1
    Enter the user id and then the film id.
    Choose the ticket type NORMAL or REDUCE if you are a student, you are qualified for a discount.
    Select the currency in which you would like to receive the ticket price, you can choose from 34 currencies. 
    Send request localhost:8080/codes and check available currencies.
    Enter the row number or seat number. 
    JSON:
    {
     "ticketType":"REDUCE",
     "currency":"PLN",
     "rowsNumber":9,
     "seatInRow": 4
    }
  
  <img src="https://github.com/Gimi818/cinema/blob/master/steps/4.0_bookingTicket.PNG" width="500" heigt="700"/>

    Step 6 :
    You have received an email with a PDF ticket containing the details along with a QR code confirming your purchase.
    Scan the QR code and check purchase details
    
   <img src="https://github.com/Gimi818/cinema/blob/master/steps/5.0_ticket.PNG" width="500" heigt="700"/>


    Add the film to the database:
    POST localhost:8080/films
    Enter the film data.
    JSON:
    {
     "title": "JOHN WICK 4",
     "category": "ACTION",
     "durationFilmInMinutes": 143
    }
  
  <img src="https://github.com/Gimi818/cinema/blob/master/steps/7.0addfilm.PNG" width="500" heigt="700"/>
  
    Create a screening:
    POST localhost:8080/screenings/1
    Enter the id in URL of the film for which you want to create.
    Enter date and time of screening.
    JSON:
    {
     "date": "2023-11-12",
     "time": "22:45"
     }
  
  <img src="https://github.com/Gimi818/cinema/blob/master/steps/8.0addScreenings.PNG" width="500" heigt="700"/>

    Endpoints available in the application :
  
   <img src="https://github.com/Gimi818/cinema/blob/master/steps/ep.PNG" width="500" heigt="500"/>

   
  
