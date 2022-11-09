package cinema

import java.lang.IndexOutOfBoundsException

fun showSeats(cinemaList: MutableList<MutableList<String>>) {

    println("Cinema:")
    for (i in 0..cinemaList[0].size) {
        print(if (i == 0) " " else " $i")
    }
    println()
    for (i in 0 until cinemaList.size) {
        for (j in 0..cinemaList[i].size) {
            print(if (j == 0) "${i + 1}" else " ${cinemaList[i][j - 1]}")
        }
        println()
    }
}

fun buyTicket(cinemaList: MutableList<MutableList<String>>): Int {
    do {
        var price = 0
        println("Enter a row number:")
        val row = readln().toInt()
        println("Enter a seat number in that row:")
        val seat = readln().toInt()
        try {
            if (cinemaList[row - 1][seat - 1] == "B") {
                throw Exception("That ticket has already been purchased!")
            } else {
                cinemaList[row - 1][seat - 1] = "B"
                price = if (cinemaList.size * cinemaList[0].size <= 60 || row <= cinemaList.size / 2) 10 else 8
                println("Ticket price: \$$price")
                return price
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        } catch (e: Exception) {
            println(e.message)
        }
    } while (price == 0)
    return 0
}

fun showStats(cinemaList: MutableList<MutableList<String>>, purchased: Int, income: Int, total: Int) {
    println("Number of purchased tickets: $purchased")
    println("Percentage: " + String.format("%.2f", purchased.toDouble() * 100.0 / (cinemaList.size * cinemaList[0].size)) +"%")
    println("Current income: \$$income")
    println("Total income: \$$total")
}

fun printMenu() {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun main() {
    println("Enter the number of rows:")
    val numRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val numSeats = readln().toInt()
    val cinemaList = MutableList(numRows) { MutableList(numSeats) { "S" } }
    var purchased = 0
    var income = 0
    val total = when (numRows * numSeats <= 60) {
        true -> numRows * numSeats * 10
        false -> numRows / 2 * numSeats * 10 + numRows / 2 * numSeats * 8 + numRows % 2 * numSeats * 8
    }

    do {
        printMenu()
        val choice = readln().toInt()
        when (choice) {
            0 -> println()
            1 -> showSeats(cinemaList)
            2 -> {
                income += buyTicket(cinemaList)
                purchased += if (income != 0) 1 else 0
            }
            3 -> showStats(cinemaList, purchased, income, total)
            else -> println("Unknown number")
        }
    } while (choice != 0)

}