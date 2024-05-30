package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.MySQLDatabase;
import Database.PolyBayDatabase;
import Models.Product;

public class ProductDAO
{
	private MySQLDatabase database;

    public ProductDAO() throws SQLException
    {
        this.database = new PolyBayDatabase();
    }

    public ArrayList<Product> findAll() throws SQLException
    {
        PreparedStatement statement = this.database.prepareStatement("SELECT * FROM product;");
        ResultSet results = statement.executeQuery();

        ArrayList<Product> sports = new ArrayList<>();

        while(results.next())
        {
            Product sport = new Product(results.getInt("id"), results.getString("name"), results.getString("owner"), results.getFloat("bid"));
            sports.add(sport);
        }

        return sports;
    }

    public Product bid(int id) throws SQLException
    {
        PreparedStatement bidUpdateStatement = this.database.prepareStatement("UPDATE product SET bid = bid + 50 WHERE id = ?;");
        bidUpdateStatement.setInt(1, id);
        bidUpdateStatement.execute();

        PreparedStatement bidResultStatement = this.database.prepareStatement("SELECT * FROM product WHERE id = ?;");
        bidResultStatement.setInt(1, id);
        ResultSet bidResult = bidResultStatement.executeQuery();

        bidResult.next();
        return new Product(bidResult.getInt("id"), bidResult.getString("name"), bidResult.getString("owner"), bidResult.getFloat("bid"));
    }
}