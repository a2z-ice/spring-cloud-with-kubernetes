import com.zaxxer.hikari.HikariDataSource

fun main(){
    // create a dataSource
    val dataSource = HikariDataSource()
    val dataSourceMysql = HikariDataSource()

    // set the jdbcUrl
    dataSource.jdbcUrl = "jdbc:postgresql://localhost:5432/postgres"
    dataSourceMysql.jdbcUrl = "jdbc:mysql://localhost:3306/test?useSSL=false"

    // set the username
    dataSource.username = "myusername"
    dataSourceMysql.username = "root"

    // set the password
    dataSource.password = "mypassword"
    dataSourceMysql.password = "123456"

    // get a connection
    val connection = dataSource.connection
    val connectionMysql = dataSourceMysql.connection

    // the query is only prepared not executed
    val query = connection.prepareStatement("SELECT * FROM users")
    val queryMysql = connectionMysql.prepareStatement("SELECT * FROM users")

    // the query is executed and results are fetched
    val result = query.executeQuery()
    val resultMysql = queryMysql.executeQuery()

    // an empty list for holding the results
    val users = mutableListOf<User>()
    val usersMysql = mutableListOf<User>()

    while (result.next()) {

        // getting the value of the id column
        val id = result.getInt("id")

        // getting the value of the name column
        val name = result.getString("name")

        /*
        constructing a User object and
        putting data into the list
         */
        users.add(User(id, name))
    }

    while (resultMysql.next()) {

        // getting the value of the id column
        val id = resultMysql.getInt("id")

        // getting the value of the name column
        val name = resultMysql.getString("name")

        /*
        constructing a User object and
        putting data into the list
         */
        usersMysql.add(User(id, name))
    }

    /*
    [User(id=1, name=Kohli), User(id=2, name=Rohit),
    User(id=3, name=Bumrah), User(id=4, name=Dhawan)]
     */
    println(users)
    println(usersMysql)

}
