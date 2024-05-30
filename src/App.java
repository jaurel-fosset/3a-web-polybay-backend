import Controllers.ProductsController;
import WebServer.WebServer;
import WebServer.WebServerContext;

public class App
{
    public static void main(String[] args) throws Exception
    {
        WebServer server = new WebServer();

        server.getRouter().get("/products", ProductsController::findAll);
        server.getRouter().post("/bid/:productId", (WebServerContext context) -> { ProductsController.bid(context); });

        server.listen(8080);
    }
}
