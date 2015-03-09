/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import database.Database;
import java.sql.*;
import javax.ws.rs.GET;
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
}
