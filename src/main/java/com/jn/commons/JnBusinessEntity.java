package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpDependencyInject;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpDbCrud;
import com.ccp.especifications.db.utils.CcpDbTable;
import com.ccp.especifications.db.utils.CcpDbTableField;
import com.jn.commons.tables.fields.A1D_email_api_client_error;
import com.jn.commons.tables.fields.A1D_email_api_unavailable;
import com.jn.commons.tables.fields.A1D_email_message_sent;
import com.jn.commons.tables.fields.A1D_email_reported_as_spam;
import com.jn.commons.tables.fields.A1D_email_try_to_send_message;
import com.jn.commons.tables.fields.A1D_failed_unlock_token_today;
import com.jn.commons.tables.fields.A1D_instant_messenger_api_unavailable;
import com.jn.commons.tables.fields.A1D_instant_messenger_bot_locked;
import com.jn.commons.tables.fields.A1D_instant_messenger_message_sent;
import com.jn.commons.tables.fields.A1D_instant_messenger_try_to_send_message;
import com.jn.commons.tables.fields.A1D_job_user_stats;
import com.jn.commons.tables.fields.A1D_locked_password;
import com.jn.commons.tables.fields.A1D_locked_token;
import com.jn.commons.tables.fields.A1D_login;
import com.jn.commons.tables.fields.A1D_login_conflict;
import com.jn.commons.tables.fields.A1D_login_conflict_solved;
import com.jn.commons.tables.fields.A1D_login_token;
import com.jn.commons.tables.fields.A1D_logout;
import com.jn.commons.tables.fields.A1D_password;
import com.jn.commons.tables.fields.A1D_password_tries;
import com.jn.commons.tables.fields.A1D_pre_registration;
import com.jn.commons.tables.fields.A1D_request_token_again;
import com.jn.commons.tables.fields.A1D_request_token_again_answered;
import com.jn.commons.tables.fields.A1D_request_unlock_token;
import com.jn.commons.tables.fields.A1D_request_unlock_token_answered;
import com.jn.commons.tables.fields.A1D_responder_request_token_again;
import com.jn.commons.tables.fields.A1D_responder_unlock_token;
import com.jn.commons.tables.fields.A1D_template;
import com.jn.commons.tables.fields.A1D_token_tries;
import com.jn.commons.tables.fields.A1D_unlock_token_tries;
import com.jn.commons.tables.fields.A1D_unlocked_password;
import com.jn.commons.tables.fields.A1D_unlocked_token;
import com.jn.commons.tables.fields.A1D_weak_password;
import com.jn.commons.tables.fields.A3D_candidate;
import com.jn.commons.tables.fields.A3D_candidate_resume;
import com.jn.commons.tables.fields.A3D_candidate_view_resume;
import com.jn.commons.tables.fields.A3D_denied_view_to_recruiter;
import com.jn.commons.tables.fields.A3D_grouped_views_by_recruiter;
import com.jn.commons.tables.fields.A3D_keywords_college;
import com.jn.commons.tables.fields.A3D_keywords_hr;
import com.jn.commons.tables.fields.A3D_keywords_it;
import com.jn.commons.tables.fields.A3D_keywords_operational;
import com.jn.commons.tables.fields.A3D_keywords_unknown;
import com.jn.commons.tables.fields.A3D_recruiter_domains;
import com.jn.commons.tables.fields.A3D_recruiter_view_resume;
import com.jn.commons.tables.fields.A3D_resume_exclusion;
import com.jn.commons.tables.fields.A4D_resumes_list;
import com.jn.commons.tables.fields.A4D_resumes_stats;
import com.jn.commons.tables.fields.A4D_search_resumes_list;
import com.jn.commons.tables.fields.A4D_search_resumes_stats;
import com.jn.commons.tables.fields.A5D_contact_us;

public enum JnBusinessEntity  implements CcpDbTable{

	//TODO COMMING SOON
	contact_us_skiped(), 
	//TODO COMMING SOON
	contact_us_ignored(), 
	//TODO COMMING SOON
	responder_unlock_token(A1D_responder_unlock_token.values()), 
	//TODO COMMING SOON
	responder_request_token_again(A1D_responder_request_token_again.values()), 
	
	contact_us(TimeOption.ddMMyyyy, A5D_contact_us.values()), 
	search_resumes_stats(TimeOption.ddMMyyyyHH, A4D_search_resumes_stats.values()), 
	search_resumes_list(TimeOption.ddMMyyyyHH, A4D_search_resumes_list.values()), 
	resumes_stats(TimeOption.ddMMyyyyHH, A4D_resumes_stats.values()), 
	resumes_list(TimeOption.ddMMyyyyHH, A4D_resumes_list.values()), 

	resume_exclusion(A3D_resume_exclusion.values()), 
	recruiter_view_resume(TimeOption.ddMMyyyy, A3D_recruiter_view_resume.values()), 
	recruiter_domains(A3D_recruiter_domains.values()), 
	keywords_unknown(A3D_keywords_unknown.values()),
	keywords_operational(A3D_keywords_operational.values()), 
	keywords_it(A3D_keywords_it.values()), 
	keywords_hr(A3D_keywords_hr.values()), 
	keywords_college(A3D_keywords_college.values()), 
	grouped_views_by_recruiter(A3D_grouped_views_by_recruiter.values()), 
	denied_view_to_recruiter(A3D_denied_view_to_recruiter.values()), 
	candidate_view_resume(TimeOption.ddMMyyyyHHmmss, A3D_candidate_view_resume.values()), 
	candidate_resume(A3D_candidate_resume.values()), 
	candidate(A3D_candidate.values()), 
	user_stats(A1D_job_user_stats.values()), 
	unlocked_token(A1D_unlocked_token.values()), 
	unlocked_password(A1D_unlocked_password.values()), 
	unlock_token_tries(A1D_unlock_token_tries.values()), 
	token_tries(A1D_token_tries.values()), 
	template(A1D_template.values()), 
	request_unlock_token_answered(TimeOption.ddMMyyyy, A1D_request_unlock_token_answered.values()), 
	request_unlock_token(TimeOption.ddMMyyyy, A1D_request_unlock_token.values()), 
	request_token_again_answered(TimeOption.ddMMyyyy, A1D_request_token_again_answered.values()), 
	request_token_again(TimeOption.ddMMyyyy, A1D_request_token_again.values()), 
	pre_registration(A1D_pre_registration.values()), 
	password_tries(A1D_password_tries.values()), 
	weak_password(A1D_weak_password.values()), 
	password(A1D_password.values()), 
	logout(TimeOption.ddMMyyyy, A1D_logout.values()), 
	login_token(A1D_login_token.values()), 
	login_conflict_solved(A1D_login_conflict_solved.values()), 
	login_conflict(A1D_login_conflict.values()), 
	login(TimeOption.ddMMyyyy, A1D_login.values()),  
	locked_token(A1D_locked_token.values()), 
	locked_password(A1D_locked_password.values()), 
	instant_messenger_bot_locked(A1D_instant_messenger_bot_locked.values()), 
	instant_messenger_api_unavailable(A1D_instant_messenger_api_unavailable.values()), 
	instant_messenger_try_to_send_message(A1D_instant_messenger_try_to_send_message.values()), 
	instant_messenger_message_sent(TimeOption.ddMMyyyyHHmmss, A1D_instant_messenger_message_sent.values()), 
	email_api_client_error(A1D_email_api_client_error.values()),
	email_try_to_send_message(TimeOption.ddMMyyyy, A1D_email_try_to_send_message.values()), 
	email_message_sent(TimeOption.ddMMyyyy, A1D_email_message_sent.values()), 
	email_reported_as_spam(A1D_email_reported_as_spam.values()),
	email_api_unavailable(A1D_email_api_unavailable.values()), 
	failed_unlock_token(TimeOption.ddMMyyyy, A1D_failed_unlock_token_today.values()), 
	;
	
	final TimeOption timeOption;
	final CcpDbTableField[] fields;
	
	
	private JnBusinessEntity(TimeOption timeOption, CcpDbTableField... keys) {
		this.timeOption = timeOption;
		this.fields = keys;
	}

	private JnBusinessEntity(CcpDbTableField... fields) {
		this.timeOption = TimeOption.none;
		this.fields = fields;
	}

	@CcpDependencyInject
	CcpDbCrud crud;


	public TimeOption getTimeOption() {
		return this.timeOption;
	}

	public CcpDbTableField[] getFields() {
		return this.fields;
	}

	public CcpDbCrud getCrud() {
		return this.crud;
	}
	
	public String getId(CcpMapDecorator values) {
		String id = this.getId(values, this.timeOption, this.fields);
		return id;
	}

	@Override
	public void saveAuditory(String id, String entityName, CcpMapDecorator values, boolean updated) {
		// TODO Auto-generated method stub
		
	}

	public boolean exceededTries(CcpMapDecorator values, String fieldName, int limit) {
		
		for(int k = 1; k <= limit; k++) {
			
			CcpMapDecorator put = values.put(fieldName, k);
			
			boolean exists = this.exists(put);
			
			if(exists == false) {
				this.save(put);
				return false;
			}
		}
		return true;
	}

	public void removeTries(CcpMapDecorator values, String fieldName, int limit) {
		
		for(int k = 1; k <= limit; k++) {
			CcpMapDecorator put = values.put(fieldName, k);
			this.remove(put);
		}
	}
	
	public void resetData(CcpMapDecorator values) {
		this.remove(values);
		this.save(values);
	}
	
	public SaveEntity getSaver(Integer status) {
		return new SaveEntity(this, status);
	}
	
	public static void loadEntitiesMetadata() {
		Object[] values = JnBusinessEntity.values();
		CcpDependencyInjection.injectDependencies(values);
	}
}

