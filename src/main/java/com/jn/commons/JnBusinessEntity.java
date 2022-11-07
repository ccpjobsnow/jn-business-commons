package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpEspecification;
import com.ccp.especifications.db.crud.CcpDbCrud;
import com.ccp.especifications.db.table.CcpDbTable;
import com.ccp.especifications.db.table.CcpDbTableField;
import com.jn.commons.tables.fields.A1D_email_message_sent_today;
import com.jn.commons.tables.fields.A1D_email_try_to_send_message;
import com.jn.commons.tables.fields.A1D_failed_unlock_token_today;
import com.jn.commons.tables.fields.A1D_locked_password;
import com.jn.commons.tables.fields.A1D_locked_token;
import com.jn.commons.tables.fields.A1D_login;
import com.jn.commons.tables.fields.A1D_login_conflict;
import com.jn.commons.tables.fields.A1D_login_request;
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
import com.jn.commons.tables.fields.A1D_static;
import com.jn.commons.tables.fields.A1D_telegram_locked_bot;
import com.jn.commons.tables.fields.A1D_telegram_message_sent_today;
import com.jn.commons.tables.fields.A1D_telegram_try_to_send_message;
import com.jn.commons.tables.fields.A1D_token_tries;
import com.jn.commons.tables.fields.A1D_unlock_token_tries;
import com.jn.commons.tables.fields.A1D_unlocked_password;
import com.jn.commons.tables.fields.A1D_unlocked_token;
import com.jn.commons.tables.fields.A1D_weak_password;
import com.jn.commons.tables.fields.A2D_job_user_stats;
import com.jn.commons.tables.fields.A3D_candidate;
import com.jn.commons.tables.fields.A3D_candidate_resume;
import com.jn.commons.tables.fields.A3D_candidate_view_resume;
import com.jn.commons.tables.fields.A3D_denied_view_to_recruiter;
import com.jn.commons.tables.fields.A3D_grouped_views_by_recruiter;
import com.jn.commons.tables.fields.A3D_keywords_college;
import com.jn.commons.tables.fields.A3D_keywords_hr;
import com.jn.commons.tables.fields.A3D_keywords_it;
import com.jn.commons.tables.fields.A3D_keywords_operational;
import com.jn.commons.tables.fields.A3D_recruiter_domains;
import com.jn.commons.tables.fields.A3D_recruiter_view_resume;
import com.jn.commons.tables.fields.A3D_resume_exclusion;
import com.jn.commons.tables.fields.A5D_contact_us_answered;
import com.jn.commons.tables.fields.A5D_contact_us_requests;
import com.jn.commons.tables.fields.A5D_responders_contact_us;

public enum JnBusinessEntity  implements CcpDbTable{

	responders_contact_us(A5D_responders_contact_us.chatId), 
	contact_us_skiped(), 
	contact_us_requests(TimeOption.ddMMyyyyHH, A5D_contact_us_requests.chatId, A5D_contact_us_requests.supportOperator, A5D_contact_us_requests.subjectType), 
	contact_us_ignored(), 
	contact_us_answered(TimeOption.ddMMyyyy, A5D_contact_us_answered.chatId, A5D_contact_us_answered.supportOperator, A5D_contact_us_answered.subjectType), 
	search_resumes_stats(), 
	search_resumes_list(), 
	resume_exclusion(A3D_resume_exclusion.resume), 
	recruiter_view_resume(TimeOption.ddMMyyyy, A3D_recruiter_view_resume.recruiter, A3D_recruiter_view_resume.resume), 
	recruiter_domains(A3D_recruiter_domains.prefix), 
	keywords_operational(A3D_keywords_operational.keyword), 
	keywords_it(A3D_keywords_it.keyword), 
	keywords_hr(A3D_keywords_hr.keyword), 
	keywords_college(A3D_keywords_college.keyword), 
	grouped_views_by_recruiter(A3D_grouped_views_by_recruiter.recruiter), 
	denied_view_to_recruiter(A3D_denied_view_to_recruiter.recruiter, A3D_denied_view_to_recruiter.resume), 
	candidate_view_resume(TimeOption.ddMMyyyyHHmmss, A3D_candidate_view_resume.email), 
	candidate_resume(A3D_candidate_resume.resume), 
	candidate(A3D_candidate.email), 
	user_stats(A2D_job_user_stats.email), 
	unlocked_token(A1D_unlocked_token.email), 
	unlocked_password(A1D_unlocked_password.email), 
	unlock_token_tries(A1D_unlock_token_tries.email), 
	token_tries(A1D_token_tries.email), 
	telegram_try_to_send_message(A1D_telegram_try_to_send_message.chatId, A1D_telegram_try_to_send_message.botId), 
	telegram_message_sent_today(TimeOption.ddMMyyyy, A1D_telegram_message_sent_today.chatId, A1D_telegram_message_sent_today.subjectType, A1D_telegram_message_sent_today.subject), 
	telegram_locked_bot(A1D_telegram_locked_bot.chatId, A1D_telegram_locked_bot.botId), 
	telegram_api_unavailable(), 
	_static(A1D_static.name), 
	responder_unlock_token(A1D_responder_unlock_token.chatId), 
	responder_request_token_again(A1D_responder_request_token_again.chatId), 
	request_unlock_token_answered(TimeOption.ddMMyyyy, A1D_request_unlock_token_answered.email), 
	request_unlock_token(TimeOption.ddMMyyyy, A1D_request_unlock_token.email), 
	request_token_again_answered(TimeOption.ddMMyyyy, A1D_request_token_again_answered.email), 
	request_token_again(TimeOption.ddMMyyyy, A1D_request_token_again.email), 
	pre_registration(A1D_pre_registration.email), 
	password_tries(A1D_password_tries.email), 
	weak_password(A1D_weak_password.email), 
	password(A1D_password.email), 
	logout(A1D_logout.email), 
	login_request(A1D_login_request.email), 
	login_conflict_solved(), 
	login_conflict(A1D_login_conflict.email), 
	login(A1D_login.email),  
	locked_token(A1D_locked_token.email), 
	locked_password(A1D_locked_password.email), 
	email_try_to_send_message(TimeOption.ddMMyyyy, A1D_email_try_to_send_message.email, A1D_email_try_to_send_message.subjectType, A1D_email_try_to_send_message.subject), 
	email_message_sent_today(TimeOption.ddMMyyyy, A1D_email_message_sent_today.email, A1D_email_message_sent_today.subjectType, A1D_email_message_sent_today.subject), 
	email_api_unavailable(), 
	failed_unlock_token_today(TimeOption.ddMMyyyy, A1D_failed_unlock_token_today.email), 
	;
	
	final TimeOption timeOption;
	final CcpDbTableField[] keys;
	
	
	private JnBusinessEntity(TimeOption timeOption, CcpDbTableField... keys) {
		this.timeOption = timeOption;
		this.keys = keys;
	}

	private JnBusinessEntity(CcpDbTableField... keys) {
		this.timeOption = TimeOption.none;
		this.keys = keys;
	}

	@CcpEspecification
	CcpDbCrud crud;


	public TimeOption getTimeOption() {
		return this.timeOption;
	}

	public CcpDbTableField[] getKeys() {
		return this.keys;
	}

	public CcpDbCrud getCrud() {
		return this.crud;
	}
	
	public String getId(CcpMapDecorator values) {
		String id = this.getId(values, this.timeOption, this.keys);
		return id;
	}

}

