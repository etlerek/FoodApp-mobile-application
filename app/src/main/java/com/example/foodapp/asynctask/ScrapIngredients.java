package com.example.foodapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.foodapp.daohandlers.IngredientDAO;
import com.example.foodapp.entity.Ingredient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ScrapIngredients extends AsyncTask<Void, Void, Void> {

    String url = "https://potrafiszschudnac.pl/diety/tabele-kalorycznosci-produktow/";
    IngredientDAO ingredientDAO;
    Document document;
    Context context;

    public ScrapIngredients(Context context) {
        this.context = context;
        ingredientDAO = new IngredientDAO(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int counter = 0;
        try {
            document = Jsoup.connect(url).get();
            for (Element row : document.select("table.prods_table tr")) {
                if (!row.select("td.td_color_0:nth-of-type(1)").text().equals("")) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(counter);
                    ingredient.setName(row.select("td.td_color_0:nth-of-type(1)")
                            .text());
                    ingredient.setKcal(Double.parseDouble(row.select("td.td_color_0:nth-of-type(2)")
                            .text().replace(",", ".")));
                    ingredient.setProtein(Double.parseDouble(row.select("td.td_color_0:nth-of-type(3)")
                            .text().replace(",", ".")));
                    ingredient.setFat(Double.parseDouble(row.select("td.td_color_0:nth-of-type(4)")
                            .text().replace(",", ".")));
                    ingredient.setSugar(Double.parseDouble(row.select("td.td_color_0:nth-of-type(5)")
                            .text().replace(",", ".")));
                    ingredientDAO.globalAdd(ingredient);
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
