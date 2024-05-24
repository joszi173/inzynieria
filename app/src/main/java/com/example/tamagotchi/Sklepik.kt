package com.example.tamagotchi

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.example.tamagotchi.db.tamagotchiDao


class Sklepik(val dao: tamagotchiDao, val gra:Gra) : Fragment(R.layout.fragment_sklepik) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);


        val layout = view.findViewById<TableLayout>(R.id.tabelaZItememi)
        val polka = layout.findViewById<TableRow>(R.id.polkaZJedzeniem)
        //val tmpButton = layout.findViewById<Button>(R.id.plcHldrButton)
        val listaItemow = dao.getAllFood()
        val newRow = TableRow(context)

        for(i in listaItemow.listIterator()){
            //set the properties for button
            //Button btnTag = new Button(this);
            val nowyPrzycisk =Button(context)
//            nowyPrzycisk.setLayoutParams(
//                WindowManager.LayoutParams(
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.WRAP_CONTENT
//                )
//            );
            println("tworze obiekt, koszt:"+i.koszt.toString())
            //btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            nowyPrzycisk.setText(i.koszt.toString());
            nowyPrzycisk.setTextColor(Color.parseColor("#000000"))
            nowyPrzycisk.setId(i.id);
            nowyPrzycisk.background = i.bitmap.toDrawable(resources)

            //add button to the layout
            newRow.addView(nowyPrzycisk);
            nowyPrzycisk.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {

                    if(dao.getAllCz().first().monety>=i.koszt) {
                        dao.dodajIloscItem(1, nowyPrzycisk.id)
                        dao.dodajMonety(-i.koszt)
                        println("dodaje item " + nowyPrzycisk.id.toString() + " o koszcie "+i.koszt)
                        gra.ZmienWyswietlaneMonety(dao.getAllCz().first().monety)
                    }else{
                        println("brak monet")
                    }
                }
            })

        }
        layout.addView(newRow)


    }



}