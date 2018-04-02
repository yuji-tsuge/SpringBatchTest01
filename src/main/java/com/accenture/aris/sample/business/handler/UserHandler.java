package com.accenture.aris.sample.business.handler;

import org.apache.ibatis.session.ResultContext;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.accenture.aris.core.support.download.DownloadHandler;
import com.accenture.aris.sample.business.entity.UserEntity;

public class UserHandler extends DownloadHandler {

	private CellProcessor[] cellProcessors = new CellProcessor[] {
			new Unique(new StrMinMax(1, 5)), // id
			new Optional(new StrMinMax(0, 16)), // name
			new Optional(new StrMinMax(0, 50)), // password
			new Optional(new StrMinMax(0, 5)), // roleId
			new Optional(new StrMinMax(0, 64)), // email
			new Optional(new StrMinMax(0, 10)), // sex
			new Optional(new StrMinMax(0, 10)), // nationality
			new Optional(new StrMinMax(0, 100)), // text
			new Optional(new StrMinMax(0, 2)), // defkey
	};
	
	private String[] header = new String[]{"id", "name", "password", "roleId", "email", "sex", "nationality", "text", "defkey"};

	@Override
	public void handleResult(ResultContext context) {
		try {			
			UserEntity user = (UserEntity)context.getResultObject();
			write(user, header, cellProcessors);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
