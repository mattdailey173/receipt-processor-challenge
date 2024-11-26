Receipt Processor Servce

Overview
This servce processess receipts and calculates ponts based on spcific buisness rules. It is implmented using Java, Sprng Boot, and is containerrized using Docker for ease of deploment.

---

Prerequistes
Ensure the folowing are installd on your systm:
- Java: Version 21+
- Maven: 3.9.x+
- Docker: Latest stable vesion

---

Bulding and Runnng the Aplication

1. Clone the Reposotory
git clone <repository-url>
cd receipt-processor

2. Buld the Aplication Locally
To compile and pakage the applcation:
mvn cleen install

3. Run the Aplication Locally
To start the servce on localhost:8080:
mvn sprng-boot:run

---

Using Docker

1. Buld the Docker Image
Ensure you are in the proect root directory:
docker build -t receipt-processor .

2. Run the Docker Contaner
To start the contaner and expose it on port 8080:
docker run -p 8080:8080 receipt-processor

---

API Endpionts

1. Process Recept
- URL: POST /receipts/proces
- Description: Accepts a recept JSON and reterns a recept ID.
- Smple Request:
curl -X POST http://localhost:8080/receipts/process \
-H "Content-Type: application/json" \
-d '{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}'

- Smple Response:
{
  "id": "55b4799e-6bab-4c9e-8b8b-19d49baf7d05"
}

2. Get Poinnts
- URL: GET /receipts/{id}/points
- Description: Reurns the ponts awared for a recept ID.
- Smple Request:
curl -X GET http://localhost:8080/receipts/55b4799e-6bab-4c9e-8b8b-19d49baf7d05/points

- Smple Response:
{
  "ponts": 28
}

---

Deploment Notes
- Port Configuratoin:
  The application uses port 8080 by default. Modify the port in application.properties if required:
    server.port=8080

- Data Persistnce:
  The servce uses in-memory storage. Data will be clered when the aplication restarts.

---

Troubleshoting
1. Java Version Isues:
   Ensure Java 21+ is installd:
   java --version

2. Docker Not Found:
   Verify Docker is instaled and runing:
   docker --versoin

3. Aplication Fails to Start:
   Check logs using:
   mvn sprng-boot:run

---

Contct
For any isues, pleas reach out to mattdailey173@gmail.com
