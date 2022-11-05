package com.jn.commons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpEspecification;
import com.ccp.especifications.db.crud.CcpDbCrud;
import com.ccp.exceptions.commons.Flow;

public enum JnBusinessEntity {

	A5D_responders_contact_us("chatId"), 
	A5D_contact_us_skiped(), 
	A5D_contact_us_requests(TimeOption.ddMMyyyyHH, "chatId", "supportOperator", "subjectType"), 
	A5D_contact_us_ignored(), 
	A5D_contact_us_answered(TimeOption.ddMMyyyy, "chatId", "supportOperator", "subjectType"), 
	A4D_search_resumes_stats(), 
	A4D_search_resumes_list(), 
	A3D_resume_exclusion("resume"), 
	A3D_recruiter_view_resume(TimeOption.ddMMyyyy, "recruiter", "resume"), 
	A3D_recruiter_domains("prefix"), 
	A3D_keywords_operational("keyword"), 
	A3D_keywords_it("keyword"), 
	A3D_keywords_hr("keyword"), 
	A3D_keywords_college("keyword"), 
	A3D_grouped_views_by_recruiter("recruiter"), 
	A3D_denied_view_to_recruiter("recruiter", "resume"), 
	A3D_candidate_view_resume(TimeOption.ddMMyyyyHHmmss, "email"), 
	A3D_candidate_resume("resume"), 
	A3D_candidate("email"), 
	A2D_job_user_stats("email"), 
	A1D_unlocked_token("email"), 
	A1D_unlocked_password("email"), 
	A1D_unlock_token_tries("email"), 
	A1D_telegram_try_to_send_message("chatId", "botId"), 
	A1D_telegram_message_sent_today(TimeOption.ddMMyyyy, "chatId", "subjectType", "subject"), 
	A1D_telegram_locked_bot("chatId", "botId"), 
	A1D_telegram_api_unavailable(), 
	A1D_static("name"), 
	A1D_responder_unlock_token("chatId"), 
	A1D_responder_request_token_again("chatId"), 
	A1D_request_unlock_token_answered(TimeOption.ddMMyyyy, "email"), 
	A1D_request_unlock_token(TimeOption.ddMMyyyy, "email"), 
	A1D_request_token_again_answered(TimeOption.ddMMyyyy, "email"), 
	A1D_request_token_again(TimeOption.ddMMyyyy, "email"), 
	A1D_pre_registration("email"), 
	A1D_password_tries(TimeOption.ddMMyyyy, "email"), 
	A1D_password_strength("email"), 
	A1D_password("email"), 
	A1D_logout("email"), 
	A1D_login_request("email"), 
	A1D_login_conflict_solved(), 
	A1D_login_conflict("email"), 
	A1D_login("email"), 
	A1D_locked_token("email"), 
	A1D_locked_password("email"), 
	A1D_email_try_to_send_message(TimeOption.ddMMyyyy, "email", "subjectType", "subject"), 
	A1D_email_message_sent_today(TimeOption.ddMMyyyy, "email", "subjectType", "subject"), 
	A1D_email_api_unavailable(), 
	;
	
	final String getId(CcpMapDecorator values,TimeOption timeOptioption, String...keys) {
		
		String formattedCurrentDate = timeOptioption.getFormattedCurrentDate();
		
		if(keys.length == 0) {
			
			if(TimeOption.none != timeOptioption) {
				return formattedCurrentDate;
			}
			
			return UUID.randomUUID().toString();
		}
		
		List<String> missingKeys = Arrays.asList(keys).stream().filter(key -> values.getAsString(key).trim().isEmpty()).collect(Collectors.toList());
		
		if(missingKeys.isEmpty() == false) {
			throw new Flow(values, 500, "The following keys are missing to compose an id: " + missingKeys + ". Current values: " + values, null);
		}
		
		
		List<String> collect = Arrays.asList(keys).stream().map(key -> values.getAsString(key).trim()).collect(Collectors.toList());
		collect = new ArrayList<>(collect);
		collect.add(0, formattedCurrentDate);
		String replace = collect.toString().replace(",", "_").replace("[", "").replace("]", "");
		return replace;
	}

	final TimeOption timeOption;
	final String[] keys;
	
	
	private JnBusinessEntity(TimeOption timeOption, String... keys) {
		this.timeOption = timeOption;
		this.keys = keys;
	}

	private JnBusinessEntity(String... keys) {
		this.timeOption = TimeOption.none;
		this.keys = keys;
	}

	@CcpEspecification
	CcpDbCrud crud;

	public CcpMapDecorator get(String id) {
		CcpMapDecorator oneById = this.crud.getOneById(id, this.name());
		return oneById;
	}

	public boolean exists(CcpMapDecorator data) {
		String id = this.getId(data, this.timeOption, this.keys);
		boolean exists = this.crud.exists(id, this.name());
		return exists;
	}
	
	public boolean save(CcpMapDecorator data) {
		String id = this.getId(data, this.timeOption, this.keys);
		boolean updated = this.crud.updateOrSave(data, id, this.name());
		return updated;
	}
	
	
	public Set<String> getSynonyms(Set<String> wordsToAnalyze){
		return new HashSet<>();
	}
	
	public static enum TimeOption{
		none{
			@Override
			String getFormattedCurrentDate() {
				return "";
			}
		}
		,ddMMyyyy
		,ddMMyyyyHH
		,ddMMyyyyHHmm
		,ddMMyyyyHHmmss
		,ddMMyyyyHHmmssSSS
		;
		String getFormattedCurrentDate() {
			return getFormattedCurrentDate(System.currentTimeMillis());
		}

		public String getFormattedCurrentDate(Long date) {
			Date d = new Date();
			d.setTime(date);
			String format = new SimpleDateFormat(this.name()).format(d);
			return format + "_";
		}
	} 
}

