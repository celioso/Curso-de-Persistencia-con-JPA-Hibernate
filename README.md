### Curso de Persistencia con JPA: Hibernate

## Preparando el ambiente

Para preparar el ambiente, necesitamos tener algunas cosas instaladas en la computadora, como:

### El eclipse
- Para instalar Eclipse, clique en el link y descargue la versión actual.

 + [The Eclipse Foundation](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2022-12/R/eclipse-inst-jre-win64.exe&mirror_id=576 "The Eclipse Foundation")

### Img-eclipse
- Luego de haberlo descargado vamos a seleccionar la versión Enterprise que permite trabajar con java EE o Jakarta (Una transición de Java EE a Jakarta EE simboliza un cambio de código comercial a código abierto no Maven Repository: com.h2database » h2 » 2.0.206 (mvnrepository.com)solo en la práctica, sino también en el nombre.). 

### img-option
- Alternativamente:

 + 1 Podemos trabajar con editores de código como intellij , visual studio code, pero estos tienen configuraciones diferentes a las que serán utilizadas en el video, sin embargo, es posible trabajar con ellos dejando como desafío usar otro editor de código.

 + [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows "IntelliJ IDEA")

 + [Visual Studio Code](https://code.visualstudio.com/Download "Visual Studio Code")
- 2 En el curso vamos a utilizar la base de datos H2 dejamos como desafío el uso de otras bases de datos.

 + [Maven Repository: com.h2database](https://mvnrepository.com/artifact/com.h2database/h2/2.0.206 "Maven Repository: com.h2database")

### Diferencia entre Hibernate y JPA

- JPA es la especificación mientras que Hibernate es una de sus implementaciones.

- JDBC es un estándar de bajo nivel para interactuar con bases de datos. JPA es un estándar de alto nivel para el mismo propósito.

### Instalar XML

1. En eclipse dar click en la opcion **Help**.
2. Seleccionar **Eclipse Marketplace**.
3. Ingresar **XML** en el buscador y como resultado te mostrará **Eclipse XML Editors and Tools**.
4. Dar click en el botón **install**.

### ¿Cuál es la mejor definición de una entidad en JPA?

Es una clase que hace el mapeamiento de una tabla del banco de datos.
Una entidad JPA funciona como un espejo de una tabla en el banco de datos.

### Haga lo que hicimos en aula: mapeando entidades
Luego de haber agregado las dependencias debemos configurar la base de datos con JPA a través del archivo persistence.xml.

En la carpeta “resources” tendremos que crear una carpeta con nombre META-INF y dentro de ella crear el archivo persistence.xml los nombres deben tener ese exacto formato ya que es como son buscados internamente por JPA

[META-INF](https://caelum-online-public.s3.amazonaws.com/1954-persistencia-jpa-hibernate/img_aula2_a.JPG "META-INF")

Dentro del archivo persistence.xml vamos a colocar las siguientes propiedades:

- Inicialmente

```xml
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

<persistence-unit name="tienda" transaction-type="RESOURCE_LOCAL">
<class>br.com.alura.loja.modelo.Produto</class><!--opcional-->
        <properties>
<property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
    <property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:tienda"/>
    <property name="javax.persistence.jdbc.user" value="sa"/>
    <property name="javax.persistence.jdbc.password" value=""/>

<property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
<property name="hibernate.hbm2ddl.auto" value="update"/>        
        </properties>
    </persistence-unit>
</persistence>
```

Luego de haber creado el archivo persistence.xml, crearemos la primera entidad dentro del package `<<com.latam.alura.tienda.modelo>>` con el nombre `<<Producto>>`.

```java
@Entity
@Table(name="productos")
public class Producto{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    public Long getId() {    return id;}

    public void setId(Long id) {    this.id = id;}

    public String getNombre() {    return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
}

    public BigDecimal getPrecio() {return precio;}

    public void setPrecio(BigDecimal precio) {this.precio = precio;}
}
```
- Y por último vamos a crear una clase main donde vamos a instanciar el modelo `<<Producto>>` y el `<<EntityManager>>` que nos va a permitir comunicarnos con la base de datos.

```java
public class RegistroDeProducto {

    public static void main(String[] args) {

        Produto celular = new Produto();
celular.setNombre("Xiaomi Redmi");
celular.setDescripcion("Producto usado");
celular.setPrecio(new BigDecimal("800"));

EntityManagerFactory factory = Persistence.
createEntityManagerFactory("tienda");

EntityManager em = factory.createEntityManager();
em.getTransaction().begin();
em.persist(celular);
em.getTransaction().commit();
em.close();

}
}
```

### Lo que aprendimos

Lo que aprendimos en esta aula:

En esta lección, aprendiste:

- Cómo configurar JPA a través del archivo de persistencia.xml;
- Cómo mapear entidades JPA;
- Cómo utilizar EntityManager para conservar entidades en la base de datos.

### Proyecto del aula anterior

¿Comenzando en esta etapa? Aquí puedes descargar los archivos del proyecto que hemos avanzado hasta el aula anterior.

[Descargue los archivos en Github](https://github.com/alura-cursos/JPA-hibernate-Alura/tree/stage-2 "Descargue los archivos en Github") o haga clic [aquí](https://github.com/alura-cursos/JPA-hibernate-Alura/archive/refs/heads/stage-2.zip "aquí") para descargarlos directamente.
