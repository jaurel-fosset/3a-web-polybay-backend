package Controllers;

import java.util.ArrayList;

import DAO.ProductDAO;
import Models.Product;
import WebServer.WebServerContext;

public class ProductsController
{
	static ProductDAO dao = null;

	public static void findAll(WebServerContext context)
	{
		try
		{
			if (dao == null)
			{
				ProductsController.dao = new ProductDAO();
			}
			
			ArrayList<Product> products = ProductsController.dao.findAll();

			context.getResponse().json(products);
		}
		catch(Exception e)
		{
			context.getResponse().serverError("[ProductsController.findAll] Error while requesting all products");
		}
		
	}

	public static void bid(WebServerContext context)
	{
		try
		{
			if (dao == null)
			{
				ProductsController.dao = new ProductDAO();
			}
			
			int productId = Integer.parseInt(context.getRequest().getParam("productId"));
			Product newProduct = ProductsController.dao.bid(productId);

			context.getResponse().json(newProduct);

			context.getSSE().emit("bids", "{ \"id\": " + productId + ", \"bid\": " + newProduct.bid() + " }");
		}
		catch(NumberFormatException e)
		{
			context.getResponse().serverError("[ProductsController.bid] " + context.getRequest().getParam("productId") + " is not a number");
		}
		catch(Exception e)
		{
			context.getResponse().serverError("[ProductsController.bid] Error while updating product : " + e.getMessage());
		}
	}
}	