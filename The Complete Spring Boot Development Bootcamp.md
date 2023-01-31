# The Complete Spring Boot Development Bootcamp



154. REST API: Getting Started

- @ConditionalOnProperty

![image-20230127093041573](The Complete Spring Boot Development Bootcamp.assets/image-20230127093041573.png)

155.

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