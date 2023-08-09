package com.jn.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInject;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.dao.CcpDao;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpField;
import com.ccp.especifications.db.utils.CcpOperationType;
import com.ccp.exceptions.db.MissingKeys;
import com.jn.commons.entities.fields.A1D_async_task;
import com.jn.commons.entities.fields.A1D_audit;
import com.jn.commons.entities.fields.A1D_email_message_sent;
import com.jn.commons.entities.fields.A1D_email_parameters_to_send;
import com.jn.commons.entities.fields.A1D_email_reported_as_spam;
import com.jn.commons.entities.fields.A1D_email_template_message;
import com.jn.commons.entities.fields.A1D_failed_unlock_token_today;
import com.jn.commons.entities.fields.A1D_http_api_error_client;
import com.jn.commons.entities.fields.A1D_http_api_error_server;
import com.jn.commons.entities.fields.A1D_http_api_parameters;
import com.jn.commons.entities.fields.A1D_http_api_retry_send_request;
import com.jn.commons.entities.fields.A1D_instant_messenger_bot_locked;
import com.jn.commons.entities.fields.A1D_instant_messenger_message_sent;
import com.jn.commons.entities.fields.A1D_instant_messenger_parameters_to_send;
import com.jn.commons.entities.fields.A1D_instant_messenger_template_message;
import com.jn.commons.entities.fields.A1D_job_user_stats;
import com.jn.commons.entities.fields.A1D_jobsnow_error;
import com.jn.commons.entities.fields.A1D_locked_password;
import com.jn.commons.entities.fields.A1D_locked_token;
import com.jn.commons.entities.fields.A1D_login;
import com.jn.commons.entities.fields.A1D_login_conflict;
import com.jn.commons.entities.fields.A1D_login_conflict_solved;
import com.jn.commons.entities.fields.A1D_login_token;
import com.jn.commons.entities.fields.A1D_logout;
import com.jn.commons.entities.fields.A1D_password;
import com.jn.commons.entities.fields.A1D_password_tries;
import com.jn.commons.entities.fields.A1D_pre_registration;
import com.jn.commons.entities.fields.A1D_record_to_reprocess;
import com.jn.commons.entities.fields.A1D_request_token_again;
import com.jn.commons.entities.fields.A1D_request_token_again_answered;
import com.jn.commons.entities.fields.A1D_request_unlock_token;
import com.jn.commons.entities.fields.A1D_request_unlock_token_answered;
import com.jn.commons.entities.fields.A1D_responder_request_token_again;
import com.jn.commons.entities.fields.A1D_responder_unlock_token;
import com.jn.commons.entities.fields.A1D_token_tries;
import com.jn.commons.entities.fields.A1D_unlock_token_tries;
import com.jn.commons.entities.fields.A1D_unlocked_password;
import com.jn.commons.entities.fields.A1D_unlocked_token;
import com.jn.commons.entities.fields.A1D_weak_password;
import com.jn.commons.entities.fields.A3D_candidate;
import com.jn.commons.entities.fields.A3D_candidate_resume;
import com.jn.commons.entities.fields.A3D_candidate_view_resume;
import com.jn.commons.entities.fields.A3D_denied_view_to_recruiter;
import com.jn.commons.entities.fields.A3D_grouped_views_by_recruiter;
import com.jn.commons.entities.fields.A3D_keywords_college;
import com.jn.commons.entities.fields.A3D_keywords_hr;
import com.jn.commons.entities.fields.A3D_keywords_it;
import com.jn.commons.entities.fields.A3D_keywords_operational;
import com.jn.commons.entities.fields.A3D_keywords_unknown;
import com.jn.commons.entities.fields.A3D_recruiter_domains;
import com.jn.commons.entities.fields.A3D_recruiter_view_resume;
import com.jn.commons.entities.fields.A3D_resume_exclusion;
import com.jn.commons.entities.fields.A4D_resumes_list;
import com.jn.commons.entities.fields.A4D_resumes_stats;
import com.jn.commons.entities.fields.A4D_search_resumes_list;
import com.jn.commons.entities.fields.A4D_search_resumes_stats;
import com.jn.commons.entities.fields.A5D_contact_us;

public enum JnEntity  implements CcpEntity{
	async_task(false, TimeOption.ddMMyyyyHHmmssSSS, A1D_async_task.values()),
	audit(A1D_audit.values()),
	candidate(A3D_candidate.values()), 
	candidate_resume(A3D_candidate_resume.values()), 
	candidate_view_resume(TimeOption.ddMMyyyyHHmmss, A3D_candidate_view_resume.values()), 
	contact_us(TimeOption.ddMMyyyy, A5D_contact_us.values()), 
	contact_us_ignored(), 
	contact_us_skiped(),
	denied_view_to_recruiter(A3D_denied_view_to_recruiter.values()), 
	email_message_sent(TimeOption.ddMMyyyy, A1D_email_message_sent.values()), 
	email_reported_as_spam(A1D_email_reported_as_spam.values()),
	email_parameters_to_send(A1D_email_parameters_to_send.values()),
	email_template_message(A1D_email_template_message.values()),
	failed_unlock_token(TimeOption.ddMMyyyy, A1D_failed_unlock_token_today.values()), 
	grouped_views_by_recruiter(A3D_grouped_views_by_recruiter.values()), 
	http_api_error_client(A1D_http_api_error_client.values()),
	http_api_error_server(A1D_http_api_error_server.values()), 
	http_api_parameters(A1D_http_api_parameters.values()),
	http_api_retry_send_request(A1D_http_api_retry_send_request.values()),
	instant_messenger_bot_locked(A1D_instant_messenger_bot_locked.values()), 
	instant_messenger_message_sent(TimeOption.ddMMyyyyHHmmss, A1D_instant_messenger_message_sent.values()),
	instant_messenger_parameters_to_send(A1D_instant_messenger_parameters_to_send.values()),
	instant_messenger_template_message(A1D_instant_messenger_template_message.values()),
	jobsnow_error(TimeOption.ddMMyyyyHH,A1D_jobsnow_error.values()),
	keywords_college(A3D_keywords_college.values()), 
	keywords_hr(A3D_keywords_hr.values()), 
	keywords_it(A3D_keywords_it.values()), 
	keywords_operational(A3D_keywords_operational.values()), 
	keywords_unknown(A3D_keywords_unknown.values()),
	locked_password(A1D_locked_password.values()), 
	locked_token(A1D_locked_token.values()), 
	login(TimeOption.ddMMyyyy, A1D_login.values()),  
	login_conflict(A1D_login_conflict.values()), 
	login_conflict_solved(A1D_login_conflict_solved.values()), 
	login_token(TimeOption.ddMMyyyy, A1D_login_token.values()), 
	logout(TimeOption.ddMMyyyy, A1D_logout.values()), 
	password(TimeOption.ddMM, A1D_password.values()), 
	password_tries(A1D_password_tries.values()), 
	pre_registration(A1D_pre_registration.values()), 
	record_to_reprocess(A1D_record_to_reprocess.values()),
	recruiter_domains(A3D_recruiter_domains.values()), 
	recruiter_view_resume(TimeOption.ddMMyyyy, A3D_recruiter_view_resume.values()), 
	request_token_again(TimeOption.ddMMyyyy, A1D_request_token_again.values()), 
	request_token_again_answered(TimeOption.ddMMyyyy, A1D_request_token_again_answered.values()), 
	request_unlock_token(TimeOption.ddMMyyyy, A1D_request_unlock_token.values()), 
	request_unlock_token_answered(TimeOption.ddMMyyyy, A1D_request_unlock_token_answered.values()), 
	responder_request_token_again(A1D_responder_request_token_again.values()), 
	responder_unlock_token(A1D_responder_unlock_token.values()), 
	resume_exclusion(A3D_resume_exclusion.values()), 
	resumes_list(TimeOption.ddMMyyyyHH, A4D_resumes_list.values()), 
	resumes_stats(TimeOption.ddMMyyyyHH, A4D_resumes_stats.values()), 
	search_resumes_list(TimeOption.ddMMyyyyHH, A4D_search_resumes_list.values()), 
	search_resumes_stats(TimeOption.ddMMyyyyHH, A4D_search_resumes_stats.values()), 
	token_tries(A1D_token_tries.values()), 
	unlock_token_tries(A1D_unlock_token_tries.values()), 
	unlocked_password(A1D_unlocked_password.values()), 
	unlocked_token(A1D_unlocked_token.values()), 
	user_stats(A1D_job_user_stats.values()), 
	weak_password(A1D_weak_password.values()), 
	;
	
	final TimeOption timeOption;
	final CcpField[] fields;
	final boolean auditable;
	
	private JnEntity(TimeOption timeOption, CcpField... fields) {
		this.auditable = TimeOption.none.equals(timeOption);
		this.timeOption = timeOption;
		this.fields = fields;
	}

	private JnEntity(CcpField... fields) {
		this.timeOption = TimeOption.none;
		this.auditable = true;
		this.fields = fields;
	}

	private JnEntity(boolean auditable, TimeOption timeOption,  CcpField... fields) {
		this.timeOption = timeOption;
		this.auditable = auditable;
		this.fields = fields;
	}

	@CcpDependencyInject
	CcpDao dao;


	public TimeOption getTimeOption() {
		return this.timeOption;
	}

	public CcpField[] getFields() {
		return this.fields;
	}

	public CcpDao getDao() {
		return this.dao;
	}
	
	public String getId(CcpMapDecorator values) {
		String id = this.getId(values, this.timeOption, this.fields);
		return id;
	}

	public void saveAuditory(CcpMapDecorator values, CcpOperationType operation) {

		if(this.auditable == false) {
			return;
		}

		String id = this.getId(values);
		CcpMapDecorator audit = new CcpMapDecorator()
		.put("id", id)
		.put("json", values)
		.put("entity", this.name())
		.put("operation", operation)

		.put("date", System.currentTimeMillis());
	
		this.dao.createOrUpdate(JnEntity.audit, audit);
	}

	public boolean exceededTries(CcpMapDecorator values, String fieldName, int limit) {
		
		for(int k = 1; k <= limit; k++) {
			
			CcpMapDecorator put = values.put(fieldName, k);
			
			boolean exists = this.exists(put);
			
			if(exists == false) {
				this.createOrUpdate(put);
				return false;
			}
		}
		return true;
	}
	
	public void resetData(CcpMapDecorator values) {
		this.delete(values);
		this.createOrUpdate(values);
	}
	
	public SaveEntity getSaver(Integer status) {
		return new SaveEntity(this, status);
	}
	
	public static void loadEntitiesMetadata() {
		Object[] values = JnEntity.values();
		CcpDependencyInjection.injectDependencies(values);
	}
	
	public CcpMapDecorator getOnlyExistingFields(CcpMapDecorator values) {
		CcpField[] fields = this.getFields();
		String[] array = Arrays.asList(fields).stream().map(x -> x.name()).collect(Collectors.toList()).toArray(new String[fields.length]);
		CcpMapDecorator subMap = values.getSubMap(array);
		return subMap;
	}
	private boolean isEmptyPrimaryKey(CcpField key, CcpMapDecorator values) {
		
		if(key.isPrimaryKey() == false) {
			return false;
		}
		
		String primaryKeyFieldValue = this.getPrimaryKeyFieldValue(key, values);
		if("_".equals(primaryKeyFieldValue)) {
			return true;
		}
		
		return false;
	}
	private String getId(CcpMapDecorator values,TimeOption timeOptioption, CcpField...fields) {

		Long time = System.currentTimeMillis();
		String formattedCurrentDate = timeOptioption.getFormattedCurrentDate(time);
		
		if(fields.length == 0) {
			
			if(TimeOption.none != timeOptioption) {
				return formattedCurrentDate;
			}
			
			return UUID.randomUUID().toString();
		}
		
		List<CcpField> missingKeys = Arrays.asList(fields).stream().filter(key -> this.isEmptyPrimaryKey(key, values))
				.collect(Collectors.toList());
		
		boolean isMissingKeys = missingKeys.isEmpty() == false;
		
		if(isMissingKeys) {
			throw new MissingKeys(this, missingKeys, values);
		}
		
		
		List<String> onlyPrimaryKeys = Arrays.asList(fields).stream().filter(key -> key.isPrimaryKey()).map(key -> this.getPrimaryKeyFieldValue(key, values)).collect(Collectors.toList());

		if(onlyPrimaryKeys.isEmpty()) {
			String hash = new CcpStringDecorator(formattedCurrentDate).hash().asString("SHA1");
			return hash;
		}
		
		Collections.sort(onlyPrimaryKeys);
		onlyPrimaryKeys = new ArrayList<>(onlyPrimaryKeys);

		if(formattedCurrentDate.trim().isEmpty() == false) {
			onlyPrimaryKeys.add(0, formattedCurrentDate);
		}
		
		String replace = onlyPrimaryKeys.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}
	
	private String getPrimaryKeyFieldValue(CcpField key, CcpMapDecorator values) {
		
		boolean notCollection = values.get(key.name()) instanceof Collection<?> == false;
		
		if(notCollection) {
			String primaryKeyFieldValue = values.getAsString(key.name()).trim() + "_";
			return primaryKeyFieldValue;
		}
		
		Collection<?> col = values.getAsObject(key.name());
		ArrayList<?> list = new ArrayList<>(col);
		list.sort((a, b) -> ("" + a).compareTo("" + b));
		String primaryKeyFieldValue = list.stream().map(x -> x.toString().trim()).collect(Collectors.toList()).toString() + "_";
		return primaryKeyFieldValue;
		
	}

	@Override
	public boolean isAuditable() {
		return this.auditable;
	}


}

