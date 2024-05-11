package com.example.tamagotchi

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.graphics.drawable.toDrawable
import com.example.tamagotchi.db.tamagotchiDao


class Sklepik(val dao: tamagotchiDao) : Fragment(R.layout.fragment_sklepik) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_tags);
        val layout = view.findViewById<FrameLayout>(R.id.sklepikLayout)

        val listaItemow = dao.getAll()

        for(i in listaItemow.listIterator()){
            //set the properties for button
            //Button btnTag = new Button(this);
            val nowyPrzycisk = Button(context)
            nowyPrzycisk.setLayoutParams(
                WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            );

            //btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            nowyPrzycisk.setText(i.koszt.toString());
            nowyPrzycisk.setId(i.id);
            nowyPrzycisk.background = i.bitmap.toDrawable(resources)

            //add button to the layout
            if (layout != null) {
                layout.addView(nowyPrzycisk)
            };
            nowyPrzycisk.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {

                    if(dao.getAllCz().first().monety>=i.koszt) {
                        dao.dodajIloscItem(1, nowyPrzycisk.id)
                        dao.dodajMonety(-i.koszt)
                        println("dodaje item " + nowyPrzycisk.id.toString() + " o koszcie "+i.koszt)
                    }else{
                        println("brak monet")
                    }
                }
            })

        }


    }



}