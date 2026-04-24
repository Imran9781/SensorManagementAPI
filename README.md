# Sensor Management API 

Overview:
This is a RESTful API which acts as a campus sensor monitoring system which was built with JAX-RS. The API allows users to monitor rooms and sensors across the campus. It lets the user track CO2 levels, temperature and occupancy levels. Data is held in memory using Hashmaps and ArrayLists and the project is split into five packages. Model, resources, exceptions, mappers and filters. With each package containing classes responsible for a specific section of the application.

Steps on How to Build & Run:

What You Need:
- Java JDK 8 or above
- Apache Maven
- Apache NetBeans IDE
- Apache Tomcat 9

1. Download the project from GitHub: https://github.com/Imran9781/SensorManagementAPI

2. Open Apache Netbeans

3. Press file then 'Open Project' and find the project from GitHub

4. Right click on Project and press 'Clean and Build'

5. Right click on project and press 'Run'

6. Then open browser and go to http://localhost:8080/SensorManagementAPI/api/v1

Sample curl Commands:

1. Get all rooms:
curl -X GET http://localhost:8080/SensorManagementAPI/api/v1/rooms

2. Create a new room:
curl -X POST http://localhost:8080/SensorManagementAPI/api/v1/rooms -H "Content-Type: application/json" -d "{\"roomName\":\"Server Room B\",\"building\":\"Tech Block\",\"floorNum\":2,\"description\":\"Secondary server room\"}"

3. Get all sensors filtered by type:
curl -X GET "http://localhost:8080/SensorManagementAPI/api/v1/sensors?type=CO2"

4. Add a reading to a sensor:
curl -X POST http://localhost:8080/SensorManagementAPI/api/v1/sensors/1/readings -H "Content-Type: application/json" -d "{\"val\":520.5,\"unit\":\"ppm\",\"note\":\"Morning reading\"}"

5. Delete a room:
curl -X DELETE http://localhost:8080/SensorManagementAPI/api/v1/rooms/3


1. Project & Application Configuration 

1.1 
The lifecycle of a JAX-RS Resource class is that once a request is sent to the API, JAX-RS creates a new instance and this is the same for every request sent to the API. However the data in DataStorage is stored as static which means it belongs to the class itself and not to any individual instance. This means all instances can access the same data so any changes made in one request such as adding a room or sensor are still visible to all other requests.

1.2
HATEOAS is when the API includes links and navigation within its responses to help the user better navigate its resources. This is beneficial because it reduces the time needed for the user to search through different sources to confirm if a URL exists, and removes the need to depend on outdated guides.

2. Room Management

2.1
When only returning the IDs the list is much smaller so it uses less data and can be done quickly, whereas when returning the full room object the request will include each room's details which uses more bandwidth and takes longer. However the positive is that the user will have all they need in one request and will not need to make any further requests.

2.2
The DELETE operation is idempotent because once the request is made it permanently removes the resource from storage. Any further use of the operation on the same resource will return an error message since the resource no longer exists. If a client mistakenly sends the same DELETE request for a room more than once, the first request will return a confirmation that the room has been deleted with a 200 OK response. Any attempt after that point will return a 404 Not Found response due to the room no longer existing. This proves that DELETE is idempotent because persistent requests will not change the final result.

3. Sensor Operations & Linking

3.1
We specifically use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method because we only want to accept requests in JSON format. Any other format sent to the API will automatically be met with a 415 Unsupported Media Type response. This ensures that only JSON data is processed by the API.

3.2
@QueryParam allows the user more freedom when filtering because they can retrieve all sensor data by calling GET /api/v1/sensors or filter by type by calling GET /api/v1/sensors?type=CO2. Using @QueryParam keeps the filter optional and separate from the URL structure, whereas @PathParam would force the user to always include a type in the URL, making it part of the resource path rather than a search filter. This is why @QueryParam is the better approach for filtering collections.

4.1
The Sub-Resource Locator pattern is a method which allows a resource class to offload requests to other classes with a dedicated purpose. In this API, SensorResource splits the requests between itself managing sensors and SensorReadingResource which focuses on all sensor reading related requests. This makes each class smaller and easier to understand and you can continue to add more sub-resource classes without impacting the existing code. This makes the overall process simpler and avoids having one large complex class.

5.2
This is because the 404 response means the URL doesn't exist which isn't the case when using /api/v1/sensors which does exist. The problem lies with the fact the roomID inside the JSON body doesn't exist in the stored data. Whereas a 422 error shows the request was received but cannot be processed due to the roomID not existing. This makes it easier to identify that the issue is with the data being sent instead of the endpoint being targeted.

5.4
Exposing internal Java stack traces to external users raises the risk of a cyber attack by allowing them to understand the structure of the API and exposing vulnerabilities to abuse. In this API the GlobalExceptionMapper prevents this by catching all errors and returning a generic 500 response which is a safe alternative to exposing the stack trace. This in turn prevents internal details being revealed to external users.