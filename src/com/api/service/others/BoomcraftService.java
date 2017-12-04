package com.api.service.others;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.AccountDao;
import com.api.dao.BonusDao;
import com.api.entitie.Account;
import com.api.entitie.Bonus;
import com.api.http.MyHttpRequest;
import com.api.service.AccountService;
import com.api.utils.Utils;

@Path("/boomcraft")
public class BoomcraftService {

	final static Logger logger = Logger.getLogger(BoomcraftService.class.getName());

	
}
