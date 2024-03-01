package api.crudkotlinspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

@SpringBootApplication
class CrudKotlinSpringApplication

fun main(args: Array<String>) {
    runApplication<CrudKotlinSpringApplication>(*args)
}

@Controller
class UserController {

    private final val url = "jdbc:postgresql://localhost:5432/postgres?"
    private final val username = "postgres"
    private final val password = "postgres"

    lateinit var connection: Connection
    lateinit var statement: Statement

    init {
        try {
            connection = DriverManager.getConnection(url, username, password)
            statement = connection.createStatement()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
    @GetMapping("/")
    fun readRoot(model: Model): String {
        val users = mutableListOf<List<String>>()
        val resultSet = statement.executeQuery("SELECT * FROM cadastro")
        while (resultSet.next()) {
            val user = mutableListOf<String>()
            for (i in 1..resultSet.metaData.columnCount) {
                user.add(resultSet.getString(i))
            }
            users.add(user)
        }
        model.addAttribute("users", users)
        return "index"
    }

    @GetMapping("/users")
    fun getUsers(model: Model): String {
        val users = mutableListOf<List<String>>()
        val resultSet = statement.executeQuery("SELECT * FROM cadastro")
        while (resultSet.next()) {
            val user = mutableListOf<String>()
            for (i in 1..resultSet.metaData.columnCount) {
                user.add(resultSet.getString(i))
            }
            users.add(user)
        }
        model.addAttribute("users", users)
        return "users"
    }

    @GetMapping("/users/{user_id}")
    @ResponseBody
    fun getUserById(@PathVariable user_id: Int): List<String> {
        val user = mutableListOf<String>()
        val resultSet = statement.executeQuery("SELECT * FROM cadastro WHERE id = $user_id")
        while (resultSet.next()) {
            for (i in 1..resultSet.metaData.columnCount) {
                user.add(resultSet.getString(i))
            }
        }
        return user
    }

    @GetMapping("/add")
    fun addUsers(): String {
        return "index"
    }

    @PostMapping("/add")
    fun postGetUsers(
            @RequestParam nome: String,
            @RequestParam sobrenome: String,
            @RequestParam email: String,
            @RequestParam cpf: String
    ): RedirectView {
        println("$nome,$sobrenome,$email,$cpf")
        statement.executeUpdate("INSERT INTO cadastro (\"Nome\", \"Sobrenome\", \"Email\", \"CPF\") VALUES ('$nome', '$sobrenome', '$email', '$cpf')")
        return RedirectView("/users")
    }

    @GetMapping("/details/{user_id}")
    fun detailsUsers(@PathVariable user_id: Int, model: Model): String {
        val userDetails = mutableListOf<String>()
        val resultSet = statement.executeQuery("SELECT * FROM cadastro WHERE id = $user_id")
        while (resultSet.next()) {
            for (i in 1..resultSet.metaData.columnCount) {
                userDetails.add(resultSet.getString(i))
            }
        }
        model.addAttribute("user", userDetails)
        model.addAttribute("user_id", user_id)
        return "update"
    }

    @PostMapping("/details/{user_id}")
    fun postUpdateUsers(
            @PathVariable user_id: Int,
            @RequestParam nome: String,
            @RequestParam sobrenome: String,
            @RequestParam email: String,
            @RequestParam cpf: String
    ): RedirectView {
        println("update")
        statement.executeUpdate("UPDATE cadastro SET \"Nome\" = '$nome', \"Sobrenome\" = '$sobrenome', \"Email\" = '$email', \"CPF\" = '$cpf' WHERE id = $user_id")
        val response = HashMap<String, String>()
        return RedirectView("/users")
    }

    @GetMapping("/delete/{user_id}")
    fun deleteUser(@PathVariable user_id: Int): ResponseEntity<Map<String, String>> {
        println("deletado")
        statement.executeUpdate("DELETE FROM public.cadastro WHERE id = $user_id")
        val response = HashMap<String, String>()
//        response["message"] = "Usuário deletado com sucesso"
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/update/{user_id}")
    fun updateUser(@PathVariable user_id: Int): ResponseEntity<Map<String, String>> {
//        println("update")
        val response = HashMap<String, String>()
        return ResponseEntity.ok().body(response)
    }

}
