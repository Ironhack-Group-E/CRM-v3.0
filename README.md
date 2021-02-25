# CRM-v3.0

The latest version of CRM moves away from the command console to an API interface, where you will be able to manage your sales representatives, potential leads, opportunities, contacts and accounts.

It also allows you to generate pdf reports with all the necessary statistics to manage efficiently your business.

## Instalation

1. Download the project from the repository. 

2. Open each of the Maven projects found inside it on an IDE as IntelliJ.

3. Configuration server is located in the following git repository:

  https://github.com/Ironhack-Group-E/configuration-repo
  
4. Go into the application.properties files of this repo and replace these lines with your mySQL username and password.
```
'spring.datasource.username=username' and 'spring.datasource.password=password'
```
5. Run each project with the command `mvn spring-boot:run`, you must run in first place **configuration-service** and **eureka-service**, afterwards run the remaining services.

## Functionalities 

The functionalities are shown below, followed by a detailed description of each route for these functionalities.

- **NEW SALESREP:** Use this route to add a new sales representative.
- **NEW LEAD:** This one is used to create new leads.
- **SHOW <SalesRep, Lead, Contact, Opportunity>** : It will show a list of all the objects of that class stored in the database.
- **CONVERT <ID>:** Use this route to convert a lead into an opportunity
- **LOOKUP <Object> <id>:** Enter the type of object you are searching and the specific id and all its information will
  be shown on screen.
- **CLOSE-WON <id>**: Closes an opportunity and marks it as won.
- **CLOSE-LOST <id>**: Closes an opportunity and marks it as lost.
- **REPORT <
  Lead/Opportunity/CLOSED-WON/CLOSED-LOST/OPEN>                                                                     
  by <SalesRep/Product/Country/City/Industry>**: Find the report you need about your opportunities by Salesrep, product,
  country, city or industry.
- **MEAN <EmployeeCount/Quantity/Opps per Account>**: Get the Mean of your employee count, quantity, and opportunities.
- **MEDIAN <EmployeeCount/Quantity/Opps per Account>**: Get the Median of your employee count, quantity, and
  opportunities.
- **MAX <EmployeeCount/Quantity/Opps per Account>**: Get the Max of your employee count, quantity, and opportunities.
- **MIN <EmployeeCount/Quantity/Opps per Account>**: Get the Min of your employee count, quantity, and opportunities.

## Extra Functionalities 

- PDF GENERATOR: A request that generates a pdf with all the reports and stats.

## Diagrams

- Case Diagram:

![alt text](https://github.com/Ironhack-Group-E/CRM-v3.0/blob/main/The-Exceptionalists-CRM-v2.0/src/main/resources/diagrams/TheDataLayer-UseCase-Diagram.png)

- Class Diagram:

![alt text](https://github.com/Ironhack-Group-E/CRM-v3.0/blob/main/The-Exceptionalists-CRM-v2.0/src/main/resources/diagrams/CRM_Exceptionalists.jpeg)

- Microservices Diagram

![alt text](https://github.com/Ironhack-Group-E/CRM-v3.0/blob/main/The-Exceptionalists-CRM-v2.0/src/main/resources/diagrams/microservices-diagram.jpg)

## ROUTES

### NEW SALES REPRESENTATIVE

Just a name is needed to create a sales representative. The corresponding ID will be shown on the screen.
``` 
POST ROUTE: http://localhost:8083/new-salesrep
No Auth needed.

Body:
{
    "name": "Luis Perez"
}
```

### NEW LEAD 

To create a lead you need: a name, an email, a company name a phone number and the sales rep's id.

``` 
POST ROUTE: http://localhost:8083/new-lead
No Auth needed.

Body:
{
    "name": "Jorge Lopez", 
    "email": "jorge22@gmail.com",
    "companyName": "Pfizer",
    "phoneNumber": "+34 666111222", 
    "salesRepId": 1
}
```

### CONVERT LEAD TO OPPORTUNITY (CREATING A NEW ACCOUNT)

In case you want to convert a lead to an opportunity for a new customer, you must also specify the account details, besides the purchase information.
``` 
POST ROUTE: http://localhost:8083/lead/{leadID}
No Auth needed.

Body:
{
    "purchase": {
        "product": "HYBRID",
        "quantity": 20
    },
    "account": {
        "industry": "PRODUCE",
        "employeeCount": 100,
        "city": "Madrid",
        "country": "Spain"
    } 
}
```

### CONVERT LEAD TO OPPORTUNITY (LINKING IT TO AN EXISTING ACCOUNT)

If the customer's account already exist, you just need to enter the purchase details.
``` 
POST ROUTE: http://localhost:8083/lead/{leadId}/account/{accountId}

No Auth needed.

Body:
{
    "product": "BOX",
    "quantity": 40 
}
```

### SHOW SALESREP/LEADS/OPPORTUNITIES/ACCOUNTS/CONTACTS

If you need to bring all the information of some class, you just need to use the following route:
``` 
GET ROUTE: http://localhost:8083/{salesrep/lead/opportunity/contact/account}   e.g.  http://localhost:8083/contact

No Auth needed.
No Body required.
```

### LOOKUP SALESREP/LEADS/OPPORTUNITIES/ACCOUNTS/CONTACTS

If you require more specific information about a particular class, you just need to provide the class and the id:
``` 
GET ROUTE: http://localhost:8083/{salesrep/lead/opportunity/contact/account}/{salesrepId/leadId/opportunityId/contactId/accounId}  e.g. http://localhost:8083/contact/3

No Auth needed.
No Body required.
```

### CLOSE OPPORTUNITY (CLOSED-LOST OR CLOSED-WON)

In case the opportunity eventually arrange an agreement or not, you can close the opportunity with the following URL:
``` 
GET ROUTE: http://localhost:8083/close-opportunity/{opportunityId}?status={closed_won, closed-lost} e.g. http://localhost:8083/close-opportunity/1?status=closed_won

No Auth needed.
No Body required.
```

### REPORTING FUNCTIONALITIES

There are many options for reporting, you can mix any of this combinations: <Opportunity/CLOSED-WON/CLOSED-LOST/OPEN> by <SalesRep/Product/Country/City/Industry>

**OPPORTUNITY BY <SALESREP/PRODUCT/COUNTRY/CITY/INDUSTRY>**

``` 
GET ROUTE:  http://localhost:8083/report/opportunity/by/{salesrep/product/country/city/industry}

No Auth needed.
No Body required.
```

**</CLOSED-WON/CLOSED-LOST/OPEN> BY <SALESREP/PRODUCT/COUNTRY/CITY/INDUSTRY>**

``` 
GET ROUTE:  http://localhost:8083/report/opportunity/{closed-won/closed-lost/open}/by/{salesrep/product/country/city/industry}

No Auth needed.
No Body required.
```

### EMPLOYEE STATS

To report the stats of the number of employees of the companies:
``` 
GET ROUTE:  http://localhost:8083/report/{min/max/mean/median}/employee-count

No Auth needed.
No Body required.
```

### QUANTITY STATS

In case you need to report the stats of the quantity of products, you can use the following route:
``` 
GET ROUTE:  http://localhost:8083/report/{min/max/mean/median}/quantity

No Auth needed.
No Body required.
```

### QUANTITY STATS

To report the stats of the quantity of products
``` 
GET ROUTE:  http://localhost:8083/report/{min/max/mean/median}/quantity

No Auth needed.
No Body required.
```

### STATS OPPORTUNITIES PER ACCOUNT

Use this route to get the stats of opportunities per account:
``` 
GET ROUTE:  http://localhost:8083/report/{min/max/mean/median}/opportunities/per/account

No Auth needed.
No Body required.
```

### PDF GENERATOR

Use this route to get a pdf with all the reports and stats (the file is created in /src/main/resources/reports/):
``` 
GET ROUTE:  http://localhost:8083/generate/pdf/reports

No Auth needed.
No Body required.
```


