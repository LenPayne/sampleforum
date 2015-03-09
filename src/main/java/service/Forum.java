/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import database.Database;
import java.sql.*;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author c0587637
 */
@Path("/forum")
public class Forum {
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(Database.getResults("SELECT * FROM forum")).build();
    }
    
    @POST
    @Consumes("application/json")
    public Response addOne(JsonObject json) {
        String name = json.getString("name");
        String text = json.getString("text");
        int result = Database.doUpdate("INSERT INTO forum (name, postText, time) VALUES (?, ?, NOW())", name, text);
        if (result <= 0)
            return Response.status(500).build();
        else
            return Response.ok().build();
    }
}
