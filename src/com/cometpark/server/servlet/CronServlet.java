package com.cometpark.server.servlet;

import static com.cometpark.server.db.OfyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cometpark.server.db.LotStatusStore;
import com.cometpark.server.gcm.BaseServlet;

public class CronServlet extends BaseServlet {
	
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		ofy().save().entities(LotStatusStore.work2());
	}

}
