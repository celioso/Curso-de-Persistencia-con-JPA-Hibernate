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

## Haga lo que hicimos en aula: mapeando relacionamientos

Ha llegado el momento de que sigas todos los pasos que he dado durante esta lección. En caso de que ya lo hayas hecho, excelente. Si aún no lo ha hecho, es importante que realice lo que se vio en los videos para que pueda continuar con la siguiente lección.

- Lo primero que vamos a realizar es organizar el código delegando la responsabilidad de instanciar el EntityManager a una clase utilitaria cuya única función será instanciarlo.

```java
public class JPAUtils {

    private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tienda");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
```

- Luego vamos a realizar el mapeamiento entre entidades esta relación puede ser entre una entidad y un Enum o entre dos relaciones.

- Para relacionamientos entre entidades y Enum:

Se debe modificar la entidad y utilizar la anotación adecuada para el tipo Enum de esta forma JPA sabrá reconocer el elemento Enum.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String nombre;
private String descripcion;
private BigDecimal precio;
private LocalDate fechaDeRegistro = LocalDate.now();   
@Enumerated(EnumType.STRING)
private Categoria categoria;
```

- Crear la clase de tipo Enum.

```java
public enum Categoria {
CELULARES,
TABLETS,
LIBROS;
}
```

- Para relacionamientos entre dos entidades

![Relacionamiento entre dos entidades](https://caelum-online-public.s3.amazonaws.com/1954-persistencia-jpa-hibernate/img_aula3_a.JPG "Relacionamiento entre dos entidades")

- Se debe añadir la nueva entidad como atributo en la entidad principal con la diferencia que vamos a utilizar la anotación para mapear relaciones de cardinalidad.

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private LocalDate fechaDeRegistro = LocalDate.now();   
    @ManyToOne
    private Categoria categoria;
```

- Debe ser creada esa entidad al igual que se hizo con la entidad producto.

```java
@Entity
@Table(name="categorias")
public class Categoria {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private String nombre;

public Categoria() {}

public Categoria(String nombre) {
this.nombre = nombre;
}

public Long getId() {return id;    }

public void setId(Long id) {this.id = id;}

public String getNombre() {return nombre;}

public void setNombre(String nombre) {this.nombre = nombre;}

}
```

- Por último, continuando con la organización del proyecto se crea una clase DAO donde se van a configurar las operaciones de acceso a la base de datos.

```java
public class ProdutoDao {

    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

}

public class CategoriaDao{

    private EntityManager em;

public CategoriaDao(EntityManager em) { 
        this.em = em;
        }

        public void cadastrar(Categoria categoria) {
            this.em.persist(categoria);
        }

 }
```

Y la clase main ahora se vería de la siguiente forma:

```java
public class RegistroDeProducto {

    public static void main(String[] args) {
        Categoria celulares = new Categoria("CELULARES");

        Producto celular = new Producto("Xiaomi Redmi", "Muy bueno", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtils.getEntityManager();

        ProductoDao productoDao = new ProductoDao(em);
                CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        categoriaDao.guardar(celulares);
        productoDao.guardar(celular);    

        em.getTransaction().commit();
        em.close();
    }

}
```
### Lo que aprendimos

Lo que aprendimos en esta aula:

En esta lección, aprendiste:

- Cómo escribir una clase DAO usando JPA;
- Cómo asignar atributos de tipo Enum en una entidad;
- Cómo mapear una relación entre entidades.

### Proyecto del aula anterior

¿Comenzando en esta etapa? Aquí puedes descargar los archivos del proyecto que hemos avanzado hasta el aula anterior.

[Descargue los archivos en Github](https://github.com/alura-cursos/JPA-hibernate-Alura/tree/stage-3 "Descargue los archivos en Github") o haga clic [aquí](https://github.com/alura-cursos/JPA-hibernate-Alura/archive/refs/heads/stage-3.zip "aquí") para descargarlos directamente.

### Haga lo que hicimos en aula: ciclo de vida de una entidad

Aquí vimos los diferentes estados de una entidad y como pasar de un estado a otro utilizando JPA.

![managed](https://caelum-online-public.s3.amazonaws.com/1954-persistencia-jpa-hibernate/img_aula4_a.JPG "managed")

Para eso vimos el esquema gráfico donde comenzamos por el estado transiente que tiene una entidad al ser instanciada, luego pasamos al estado Managed utilizando el método persist() de JPA hasta el momento que sincronizamos la información con la base de datos utilizando el método `flush()` o `commit()` de JPA.

Estas transiciones de estados fueron realizadas en el DAO (Data Access Object) de la clase Producto y de la clase Categoria donde configuramos los métodos para persistir una entidad, para actualizar o modificar una entidad ya existente o para eliminar un registro de la base de datos. Nuestro DAO para la clase producto quedó de la siguiente forma al igual que para categoría:

```java
public class ProductoDao {

    private EntityManager em;

    public ProductoDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Producto producto) {
        this.em.persist(producto);
    }

    public void actualizar(Producto producto) {
        this.em.merge(producto);
    }

    public void remover(Producto producto) {
        categoria=this.em.merge(producto);
        this.em.remove(producto);
    }

}
```

### Lo que aprendimos

Lo que aprendimos en esta aula:

En esta lección, aprendiste:

- Cómo funciona el ciclo de vida de las entidades JPA;
- Las transiciones de estado de una entidad cuando persisten;
- Las transiciones de estado de una entidad cuando se actualizan;
- Las transiciones de estado de una entidad cuando se quita.

###  Proyecto del aula anterior

¿Comenzando en esta etapa? Aquí puedes descargar los archivos del proyecto que hemos avanzado hasta el aula anterior.

[Descargue los archivos en Github](https://github.com/alura-cursos/JPA-hibernate-Alura/tree/stage-4 "Descargue los archivos en Github") o haga clic [aquí](https://github.com/alura-cursos/JPA-hibernate-Alura/archive/refs/heads/stage-4.zip "aquí") para descargarlos directamente.