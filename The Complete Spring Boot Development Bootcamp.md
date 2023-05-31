# The Complete Spring Boot Development Bootcamp

Notice the spring-boot-devtools dependency which speeds up development by ensuring the maven project is rebuilt whenever there are changes.

## Section 8: Model View Controller

### 62. The MVC Design

- View - what the user sees
- Model - represents the data needed by the view
- Controller - allows the view and model to interact
  - responds to requests by managing Model and presenting view

### 63. The Controller

    ![image-20230206190243554](assets/image-20230206190243554.png)
  - The class annotated with @Controller is an entry point for web requests
  - the controller maps the request to a handler method that matches the path
  - the handler method tries responding with a view called "somehtmlview"
    - actually looks inside the templates folder for somehtmlview, but there is nothing so an error code is returned.


### 70. The Model

- Download Java Code Generators extension for vs code
- We create a Grade.java class with three fields and generate all the constructors and getters and setters with this extension
- ![image-20230206191652438](assets/image-20230206191652438.png)

![image-20230206191911191](assets/image-20230206191911191.png)

### 75. Thymeleaf: Combining Model and View

**Variable Expression**

- prefixed by $
- executes on a model attribute
- eg. `${age}`

![image-20230206193045157](assets/image-20230206193045157.png)

- Make sure there are public getters for the needed attributes or thymeleaf will not be able to read them

### 76. **Selection expression**

![image-20230206194047998](assets/image-20230206194047998.png)

`th:object` binds an object to the element

Then selection expressions (prefixed by star) can select attributes from the previously bound object.

### 78. Thymeleaf conditionals

`th:if` will render if condition is true

`th:unless` will not render unless condition is true

![image-20230206195100813](assets/image-20230206195100813.png)

Alternatively, you can use conditionals in `th:text`

![image-20230206195309459](assets/image-20230206195309459.png)

`th:switch`

![image-20230206195605442](assets/image-20230206195605442.png)

80. Utility methods

![image-20230206200318863](assets/image-20230206200318863.png)

### Cheatsheet from model view controller section

https://www.learnthepart.com/course/af54547f-e993-47bd-ad51-d7c7270c4e50/9996e227-3402-4553-9009-dfbdbe1d5d64

## Section 9: Challenge 2

### Flash Attributes - data that survives a redirect

https://www.learnthepart.com/course/af54547f-e993-47bd-ad51-d7c7270c4e50/84f77423-b012-4ba3-bc90-6f2b2625ef49


## Section 12: Three Layer Codebase

### 121. Three layer architecture

![image-20230208171305308](assets/image-20230208171305308.png)

![image-20230208171534044](assets/image-20230208171534044.png)

How to refactor our Controller to a Repository class

- All CRUD (create read update delete) operations should only occur inside the repository
- Strip all CRUD operations from the Controller class

How to refactor our Controller to a Service class

- Service must act as a middleman between Controller and Repository
- Controller should not interact with Repository directly
- Move every business operation from Controller into Service
- Move every interaction with the repository into service

## Section 14: Beans and Dependency Injection

1. A bean is an object that lives inside the Spring Container.
2. As your `@SpringBootApplication` performs a `@ComponentScan`, a bean is created from classes marked with `@Component`.
3. `@Controller`, `@Service` and `@Repository` derive from `@Component`.
4. `@Configuration`: marks a class as a source for bean definitions.
5. `@Bean`: method-level annotation for bean definitions.
6. `@Autowired` injects the bean where it's needed.

https://www.learnthepart.com/course/af54547f-e993-47bd-ad51-d7c7270c4e50/e210efe0-6347-4914-b6e9-3abe7e0755f0

## Section 15: Challenge 5

### 137. Autowired Vs Constructor

- No ![image-20230209093546914](assets/image-20230209093546914.png)
- almost![image-20230209093608241](assets/image-20230209093608241.png)
- Yes![image-20230209093627449](assets/image-20230209093627449.png)
- You do not need to add @Autowired at all for Spring Boot to know that the repositories need to be injected. Spring Boot will look at the constructors and wire all the dependencies needed

To refactor the project for this challenge, add constructors to take advantage of Spring Boot's intelligent dependency injection

- Note - Leave the StoreService storeService part, just delete new StoreService(); Still need that variable to be set

![image-20230209094949385](assets/image-20230209094949385.png)

![image-20230209095500981](assets/image-20230209095500981.png)

- Note - @Autowired is redundant and can be removed

## Section 16: Testing a Web Application

139. The importance of dependency injection for unit testing

- You can unit test a class only if it's loosely coupled
- If a dependency is tightly coupled, the dependency's logic will always interfere with the logic of the class that we want to test
- ![image-20230209100213042](assets/image-20230209100213042.png)

141. Setting up Testing Class

- We test the GradeService in this example

- The gradeRepository will be mocked
  - We will be able to control exactly what the gradeRepository can or can't return
- @InjectMocks will inject the mock gradeRepository into the gradeService

![image-20230209101811131](assets/image-20230209101811131.png)

143. Unit Testing the Service Class

You can test how many times a mock's method is called using verify() ![image-20230209104342056](assets/image-20230209104342056.png)

146. Integration Testing

We must use a MockMvc to simulate web requests

![image-20230210163556939](assets/image-20230210163556939.png)

A static import must be made for resultmatchers

![image-20230210164608480](assets/image-20230210164608480.png)

![image-20230210165458900](assets/image-20230210165458900.png)

Use status(), view() and model() from MockMvcResultMatchers to validate the layers of the application



### Cheat Sheet

### Unit Testing

------

```
@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {


    @Mock
    private Dependency dependency;

    @InjectMocks
    private Service service;

}
```

1. `@RunWith(MockitoJUnitRunner.class)`: class-level annotation where the target class can run tests.
2. `@Mock`: mocks a dependency.
3. `@InjectMocks`: creates an object and injects every mock into it.
4. `@Test`: method-level annotation that can run a test.

------

```
    @Test
    public void someUnitTest() {

        //1. Arrange: prepare the data needed to carry out the test.
        when(dependency.method()).thenReturn(someData); 

        //2. Act: call the method you want to test.
        Type result = service.method()

	//3. Assert: verify that the method is behaving correctly.
        assertEquals(expect, result);
        verify(mock, times(number of invokations)).method()
    }
```

### Integration Testing

------

```
@SpringBootTest //starts up application context
@AutoConfigureMockMvc  // Configures the Mockmvc Bean
class TestClass {

    @Autowired
    private MockMvc mockMvc;

    @Test 
    public void test() throws Exception {

    // 1. Create request.

    RequestBuilder getRequest = MockMvcRequestBuilders.get("/path");
    RequestBuilder postRequest = MockMvcRequestBuilders.post("/path");

    // 2. Perform Request.

    mockMvc.perform(getRequest)

    mockMvc.perform(postRequest)
      .param(param, value)
      .param(param, value)
      .param(param, value)
      .param(param, value)

    // 3. Verify status, view, model, etc...

      .andExpect(status().isxxxSuccessful())

      .andExpect(view().name("view"))

      .andExpect(model().attributeExists("modelAttribute"));

      .andExpect(redirectedUrl("/path"));
    }


}
```
## Section 19: Rest API
### 154. REST API: Getting Started

- @ConditionalOnProperty will only load the bean if the condition is satisfied
- Can be useful if you have eg. different beans of the same type

![image-20230127093041573](The Complete Spring Boot Development Bootcamp.assets/image-20230127093041573.png)



### 155. REST API: GET Operation

- @GetMapping
- @Controller + @ResponseBody
  - @ResponseBody serializes an object to JSON
- @RestController
- Not that you can just annotate controller as @RestController instead of adding @Controller and @ResponseBody
- ![image-20230127112548509](The Complete Spring Boot Development Bootcamp.assets/image-20230127112548509.png)
- Equivalent:![image-20230127112816371](The Complete Spring Boot Development Bootcamp.assets/image-20230127112816371.png)
- Also equivalent:![image-20230127112657063](The Complete Spring Boot Development Bootcamp.assets/image-20230127112657063.png)

## @RequestParam vs @PathVariable

Both are used to extract values from the request

![image-20230130201503387](assets/image-20230130201503387.png)@PathVariable is more suitable for REST. See docs. https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch18s02.html

158. Rest API: POST Operation

**@RequestBody** annotation is necessary for spring boot to deserialize the posted json data to a Contact object.

![image-20230213095450540](assets/image-20230213095450540.png)

## Section 22: SQL Database (Spring Boot JPA)

174. JPA and H2

Trick to add common spring dependencies to a pom.xml

![image-20230214152644709](assets/image-20230214152644709.png)

I had to manually click on the final <enter to proceed> part and then a popup shows up to confirm.

We need Spring boot JPA and H2 Database. Make sure it is Spring Boot JPA and not Spring JPA.

Note: paste the url from the provided application.properties into the h2 console to connect

![image-20230214154902208](assets/image-20230214154902208.png)

176. Object Relational Mapper

We can create a table in the h2 database without ever writing any SQL

Make sure to annotate one of the columns with @Id to set it as a primary key.

![image-20230214155007760](assets/image-20230214155007760.png)

177. Saving a student

We can add rows to the table without writing any SQL either.

Simply extend a CrudRepository

![image-20230214160619439](assets/image-20230214160619439.png)

Mark the student service with @Service and autowire the repository into it

![image-20230214160658089](assets/image-20230214160658089.png)

![image-20230214160718943](assets/image-20230214160718943.png)

Autowire the the service into the StudentController...

![image-20230214160812220](assets/image-20230214160812220.png)

And try sending  a POST request

![image-20230214160837261](assets/image-20230214160837261.png)

Magic!

![image-20230214160855108](assets/image-20230214160855108.png)

178. Retrieving a Student

StudentServiceImpl.java

![image-20230214161722030](assets/image-20230214161722030.png)

Studentcontroller.java

![image-20230214161734444](assets/image-20230214161734444.png)

179. Delete a Student

![image-20230214162144402](assets/image-20230214162144402.png)

![image-20230214162152752](assets/image-20230214162152752.png)

180. Lombok

Lombok helps to reduce boilerplate java code

Install Lombok extension

![image-20230214162304520](assets/image-20230214162304520.png)

Make sure you have the lombok dependency

![image-20230214162325617](assets/image-20230214162325617.png)

Remove all the getters and setters in the Student class and add @Getter and @Setter decorators from Lombok.

Lombok also has some for constructors to generate no argument constructors and all argument constructors for example.

The result is a significantly cleaned up Student class.

![image-20230214162648639](assets/image-20230214162648639.png)

Intermission: NoArgsConstructor

Official quote from JPA: *"The JPA specification requires that all persistent classes (*`*@Entity*`*) have a no-arg constructor. This constructor may be public or protected. Because the compiler automatically creates a default no-arg constructor when no other constructor is defined, only classes that define  constructors must also include a no-arg constructor.*"

**In other words...**

If there are no constructors, Java generates one by default.

![img](assets/2022-08-01_20-37-50-1b331cf5d3751cd366e6fb56026c7d77.png)

If you're defining constructors, don't forget `@NoArgsConstructor`

![img](assets/2022-08-01_20-39-29-28cedf06f8e1a0d884c2544df9fc76bd.png)

### 182. Grade Entity

the id field will be the primary key of the grade table with column name "id" and will be a generated value.

Grade.java

![image-20230216095431419](assets/image-20230216095431419.png)

extend a CrudRepository for the grade repository and specify that it will hold objects of type Grade with a primary  key of type Long

GradeRepository.java

![image-20230216095458003](assets/image-20230216095458003.png)

To automatically populate the database with sample data have the application root implement a CommandLineRunner

GradeSubmissionApplication.java

![image-20230216095540297](assets/image-20230216095540297.png)

Wire the repository into the service and the service into the controller

![image-20230216095907998](assets/image-20230216095907998.png)

![image-20230216095920953](assets/image-20230216095920953.png)

### 183. Unidirectional: Many to One

We will create a foreign key column in the grade table referencing the student id from the student table

We specify that it has a Many to One relationship (Many grades to one student id) and that every grade must belong to a student (optional=false). This will give a request error if a POST request does not contain a student id. We could also make nullable=false in the @JoinColumn annotation but **using optional=false in the @ManyToOne annotation is best practice because it will block the request before contacting the database.** It is a runtime instruction.

Grade.java

![image-20230216101639858](assets/image-20230216101639858.png)

GradeServiceImpl.java

![image-20230216101701252](assets/image-20230216101701252.png)

GradeController.java

![image-20230216101726217](assets/image-20230216101726217.png)



![image-20230216100539382](assets/image-20230216100539382.png)

![image-20230216101604106](assets/image-20230216101604106.png)

![image-20230216101444612](assets/image-20230216101444612.png)

### 184. Read Grade by Student Id

Spring Boot can intelligently generate the implementation of your GradeRepository by reading your specified method signatures if it is written in a very specific format. 

Eg. (1) findBy (2) StudentId - this must match up with the column name (3) Long id

![image-20230216103306276](assets/image-20230216103306276.png)

GradeRepository.java

![image-20230216103022890](assets/image-20230216103022890.png)

GradeServiceImpl.java

![image-20230216103038500](assets/image-20230216103038500.png)

GradeController.java

![image-20230216103052683](assets/image-20230216103052683.png)

### 186. Bidirectional: One To Many

![image-20230216103642007](assets/image-20230216103642007.png)

Student.java

![image-20230216125720318](assets/image-20230216125720318.png)

First we replaced @AllArgsConstructor with @RequiredArgsConstructor so we can add a new grades field without breaking our implementation of the command line runner. We have to mark the required fields with @NonNull as well.

![image-20230216130433857](assets/image-20230216130433857.png)

**We must add `mappedBy` to @OneToMany on the grades field to tell spring boot that this already has a mapping with the student table or else spring boot will create an extra table like so**

![image-20230216103817530](assets/image-20230216103817530.png)

We also have to add **@JsonIgnore** to remove the grades array from the response. The reason for this is that the grades already contain a field from a Student, which will **cause looping** when spring boot tries to serialize the objects into json.

![image-20230216125130343](assets/image-20230216125130343.png)



![image-20230216130220203](assets/image-20230216130220203.png)

Intermission: Best Practice

An excerpt from the **Hibernate Best Practices**:

*"Prefer bidirectional associations:*

*Unidirectional associations are more difficult to query. In a large application,  almost all associations must be navigable in both directions in  queries."*

### 188. Cascade

In order to successfully delete a Student record, we need to add cascade because otherwise if we deleted a student then the grades table with the student id would make no sense (grade for non existing student).

Student.java

![image-20230216143817468](assets/image-20230216143817468.png)

### 189. Autowired vs AllArgsConstructor

We replace everywhere that has Autowired annotations with @AllArgsConstructor instead. Spring will perform the dependency injection based on the constructor.

![image-20230216144356972](assets/image-20230216144356972.png)

### 191. One to Many

See 184. for explanation

![image-20230216153340837](assets/image-20230216153340837.png)

### 192. Refactoring around Optional

Consider the fact that users might request something that does not exist. We risk running into null exceptions.

![image-20230216153716682](assets/image-20230216153716682.png)

![image-20230216153615104](assets/image-20230216153615104.png)

![image-20230216154646531](assets/image-20230216154646531.png)

![image-20230216154657778](assets/image-20230216154657778.png)

### 193. Finalizing the GradeServiceImpl

Note that get operations are transactional by default but delete operation is not. You must add this annotation or else the delete requests will throw an error.

![image-20230216155915343](assets/image-20230216155915343.png)

###194. Constraints: Preventing Duplicate Grades

Course.java

To ensure that there are no two courses with the same course code

![image-20230216160402649](assets/image-20230216160402649.png)

Grade.java

to ensure that only one grade is given for a unique combination of student id and course id

![image-20230216160450432](assets/image-20230216160450432.png)

Spring will throw data integrity exceptions for you with these constraints

### 195. Refactoring around Optionals - Loose Ends

![image-20230216161320964](assets/image-20230216161320964.png)

![image-20230216161338304](assets/image-20230216161338304.png)

etc...

### Cheat Sheet

- **H2:** Provides an in-memory relational database (for prototyping / development purposes).

- **MySQL / PostgreSQL:** Relational database management system used in production environments (covered in the deployment section).

- **Spring Boot JPA:** Allows a Spring Boot application to interact with an SQL database.

- **CRUD Repository**: Provides methods that can Create, Read, Update, or Delete records.

- **Entity**: Java object that Spring Boot JPA can manage.

- **`@Table`**: Specifies the table for the annotated entity.

  - `name`: Name of the table.
  - `uniqueConstraints`: Unique constraints to be placed on the table.

- **`@Column`**: Maps a field to a table column.

  - `name`: Name of column.
  - `nullable`: Boolean that determines if the column can accept nulls.
  - `unique`: Boolean that determines if the column rejects duplicates.

- **Primary Key**: Column that uniquely identifies each record inside a table.

  - `@Id`: Applied on a field that maps to a primary key.

- **Foreign Key**: References the primary key of another table.

  - When applicable, put the foreign key column in the table that cannot live without the other.

  - ```
    @JoinColumn
    ```

    : Specifies a column for joining an entity association.

    - `name`: Name of the column.
    - `referencedColumnName`: Name of the column referenced by the foreign key column.

- **`@ManyToOne`**: Many rows in the child table belong to one row in the parent table.

- **`@OneToMany`**: One row in the parent table associates with many rows in the child table.

## Challenge 8: Many to Many Relationships

### Challenge Part 1 & 2

`@JsonIgnore` since courses are associated with students and students are associated with courses we have to add this annotation to both entities to prevent loops.

`@ManyToMany` a course can have many students and students can take multiple courses.

`@JoinTable` a table course_student will be created for the many to many relationship. specify the join keys.

Note that **both tables have to own the relationship** using the @JoinTable annotation or else there will be deletion errors. A `mapped_by` like we have used previously is not enough. `mapped_by` implies a one sided owning relationship, so we use it for One to Many relationships.

![image-20230217103458646](assets/image-20230217103458646.png)

Course.java

![image-20230217102702426](assets/image-20230217102702426.png)

Student.java

![image-20230217102717963](assets/image-20230217102717963.png)

CourseServiceImpl.java

**Note that Spring Boot will handle adding rows to the new course_student join table automatically.** You do not need to add any special logic.

Note the usage of existing method `getCourse` to simplify the code. We could have done the long way of finding course by id and so on but this is much cleaner.

![image-20230217103118025](assets/image-20230217103118025.png)

CourseController.java

![image-20230217103138149](assets/image-20230217103138149.png)

Similar for Student service and controller.

## Section 24: Spring Security: Basic and JWT Authentication

### 208. Basic Auth with Spring Security Part 1

![image-20230509181057376](assets/image-20230509181057376.png)

![image-20230509181139232](assets/image-20230509181139232.png)

As of Spring Boot 2.7 the SecurityConfig class no longer has to extend a WebSecurityConfigurerAdapter.

Instead, the SecurityFilterChain bean is created directly. The filter chain will define the security rules/filters that are checked against every request.

The below filter chain configuration says that any http request that goes through the filter chain 

- should be authorized
- should be authenticated
- with basic authentication
- and stateless session management
  - without the stateless policy, spring security creates a session for a user, making it possible for them to successfully request e.g. all the contacts even after switching to No Auth in postman if they have authenticated once before. This default behaviour is **session-based authentication**.

![image-20230509180854627](assets/image-20230509180854627.png)

### 215. Getting Started: JWT Project

![image-20230531105154495](assets/image-20230531105154495.png)

Only difference in the filter chain for this starter project is these two lines.

The first is required to render H2 on your browser. The second is required so you don't have to constantly authenticate when you connect to H2. It will just allow the request.

Note - H2 is for development purposes only!

### 216. Token-based Authentication (JWT)

**JWT Token**

A JSON Web Token consists of 3 parts which are base 64 encoded:

**Header** - information about the type and algorithm used for the token

**Payload** - information about the user, token expiry

**Signature** - Result of a signing algorithm that combines the header, payload, and secret key

![image-20230531131046556](assets/image-20230531131046556.png)



Pathway one - registration

All requests will be permitted to register. Credentials will be encoded and saved.

![image-20230531130327648](assets/image-20230531130327648.png)

Pathway two - login

User logs in. If the password matches the user is granted a JWT token.

![image-20230531130415469](assets/image-20230531130415469.png)

![image-20230531130612063](assets/image-20230531130612063.png)

Pathway three - authorization

User's request now includes the JWT token they were provided. The Authorization filter will catch the request and try to verify the token provided by applying the same algorithm with a secret key no one knows. If the signature matches the token's signature the token is valid.

![image-20230531130815858](assets/image-20230531130815858.png)

The user's information gets saved inside the SecurityContextHolder.

### 217. Token-based Authentication - Part 1

Pathway 1 implementation

![image-20230531132430887](assets/image-20230531132430887.png)

UserController.java

![image-20230531132505487](assets/image-20230531132505487.png)

UserServiceImpl.java

![image-20230531132520772](assets/image-20230531132520772.png)

Test the endpoint in Postman:

![image-20230531132609364](assets/image-20230531132609364.png)

### 218. Intermission: Protecting the Password

We should not be sending back the encrypted password. We can just send back a status and no response body.

![image-20230531132913178](assets/image-20230531132913178.png)

Same Postman test:

![image-20230531133008172](assets/image-20230531133008172.png)

### 219. Token-based Authentication - Part 2

Implementing the login portion

We will create a new filter that will map the request data to a User object

AuthenticationFilter.java

![image-20230531144804461](assets/image-20230531144804461.png)

![image-20230531144747403](assets/image-20230531144747403.png)

Note line 24. Setting the authentication url will make it so that the attemptAuthentication method will not execute unless the url matches /authenticate

### 220. Side Quest: Exception Handling + Dispatcher Servlet

We cannot user the exception controller advice to catch exceptions thrown by the authentication filters because they run before the dispatcher servlet.

![image-20230531151531516](assets/image-20230531151531516.png)

Instead, we must implement our own exception handler for the filter chain. 

Extending the OncePerRequestFilter instead of implementing the generic Filter class ensures that the filter runs only once per request.

You must add filterChain.doFilter to continue the filter chain. Otherwise, the filtering will just end there.

We will wrap doFilter in a try catch block so that if any exceptions occur downstream it will be caught by the ExceptionHandlerFilter. 

![image-20230531151714543](assets/image-20230531151714543.png)

Add it to the filter chain using addFilterBefore() to make sure it runs before any other filters.

![image-20230531151735965](assets/image-20230531151735965.png)

Make sure the AuthenticationFilter also calls doFilter() to continue the chain

![image-20230531152019210](assets/image-20230531152019210.png)

