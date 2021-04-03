package com.ma.saper

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.ItemClickListener {

    private lateinit var grid: RecyclerView
    var adapter: MyRecyclerViewAdapter? = null
    private val columns = 6
    private val data: MutableList<Int> = generateData(columns)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.grid)

        val layoutManager = GridLayoutManager(this, columns)
        grid.layoutManager = layoutManager

        setAdapter()
    }

    private fun setAdapter(setClickListener: Boolean = true) {
        adapter = MyRecyclerViewAdapter(this, data)
            .apply {
                if (setClickListener)
                    setClickListener(this@MainActivity)
            }
        grid.adapter = adapter
    }

    override fun onItemClick(view: View?, position: Int) {
        val value = data[position]
        var setClickListener = true
        if (value == 0) {
            val near = calculateNearBy(position)
            data[position] = if (near > 0) near else -1
        } else if (value == 10) {
            data.withIndex().forEach {
                if (it.value == 10) {
                    data[it.index] = 20
                }
            }
            setClickListener = false
        }
        Log.d(MainActivity::class.java.simpleName, "$position : $value")
        setAdapter(setClickListener)
    }

    private fun calculateNearBy(position: Int): Int {
        val topLeft = try { data [position - columns - 1] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val top = try { data [position - columns ] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val topRight = try { data [position - columns + 1] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val left = try { data [position - 1] } catch (ex: IndexOutOfBoundsException) { 0 } == 10

        val right = try { data [position + 1] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val bottomLeft = try { data [position + columns - 1] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val bottom = try { data [position + columns ] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val bottomRight = try { data [position + columns + 1] } catch (ex: IndexOutOfBoundsException) { 0 } == 10
        val number = (topLeft.i() + top.i() + topRight.i() + left.i() + right.i() + bottomLeft.i() + bottom.i() + bottomRight.i())
        return number
    }

    private fun Boolean.i() : Int {
         return if (this) 1 else 0
    }

    private fun generateData(columns: Int): MutableList<Int> {
        val random = Random()
        val cellsWithBombs: MutableSet<Int> = mutableSetOf()
        while (cellsWithBombs.size < columns) {
            val nextCell = random.nextInt(columns * columns)
            cellsWithBombs.add(nextCell)
        }
        val field: MutableList<Int> = mutableListOf()
        for (i in 0 until columns * columns) {
            field.add(0)
        }
        cellsWithBombs.forEach {
            field[it] = 10
        }

        return field
    }
}