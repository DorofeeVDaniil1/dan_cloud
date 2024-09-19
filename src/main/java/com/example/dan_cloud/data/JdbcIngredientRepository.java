package com.example.dan_cloud.data;

import com.example.dan_cloud.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Используем 2 параметра т.к , по стандарту, RowMapper требуют два параметра объект резултат запроса sql и номер строки.
    private Ingredient mapRowToIngredient (ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        );
    }


    @Override
//используем iterable . так как это общий интерфейс от List, который нам возвращает query.
    public Iterable<Ingredient> findAll() {
        //query содержит 2 параметра sql и RowMapper<T> - Этот параметр отвечает за то, как данные, возвращаемые запросом, будут преобразованы в объекты Java.
        return jdbcTemplate.query(
                "select id,name,type from Ingredient",
                //Когда SQL-запрос возвращает строки, Spring вызывает переданный метод для каждой строки результата.
                this::mapRowToIngredient);

    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> results = jdbcTemplate.query(
                "select id, name , type from Ingredient where id=?",
                this::mapRowToIngredient,
                id);

        return results.isEmpty() ?Optional.empty():Optional.of(results.get(0));
    }

    //Метод JdbcTemplate.update() можно использовать для выполнения любых запросов,

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "insert into Ingredient (id,name,type) values (?,?,?)",
                //Getters были созданы библиотекой loombook
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return ingredient;
    }
}