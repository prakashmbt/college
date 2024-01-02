package com.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties

public class ResponseConfig {

	@Value("${INSERT_SUCCESS}")
	public String insertSuccess;

	@Value("${UPDATE_SUCCESS}")
	public String updateSuccess;

	@Value("${DELETE_SUCCESS}")
	public String deleteSuccess;

	@Value("${INSERT_ERROR}")
	public String insertError;

	@Value("${UPDATE_ERROR}")
	public String updateError;

	@Value("${DELETE_ERROR}")
	public String deleteError;

	@Value("${INSERT_EXCEPTION_ERROR}")
	public String insertExceptionError;
	 
	@Value("${UPDATE_EXCEPTION_ERROR}")
	public String updateExceptionError;

	@Value("${DELETE_EXCEPTION_ERROR}")
	public String deleteExceptionError;

	public String getInsertSuccess() {
		return insertSuccess;
	}

	public void setInsertSuccess(String insertSuccess) {
		this.insertSuccess = insertSuccess;
	}

	public String getUpdateSuccess() {
		return updateSuccess;
	}

	public void setUpdateSuccess(String updateSuccess) {
		this.updateSuccess = updateSuccess;
	}

	public String getDeleteSuccess() {
		return deleteSuccess;
	}

	public void setDeleteSuccess(String deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
	}

	public String getInsertError() {
		return insertError;
	}

	public void setInsertError(String insertError) {
		this.insertError = insertError;
	}

	public String getUpdateError() {
		return updateError;
	}

	public void setUpdateError(String updateError) {
		this.updateError = updateError;
	}

	public String getDeleteError() {
		return deleteError;
	}

	public void setDeleteError(String deleteError) {
		this.deleteError = deleteError;
	}

	public String getInsertExceptionError() {
		return insertExceptionError;
	}

	public void setInsertExceptionError(String insertExceptionError) {
		this.insertExceptionError = insertExceptionError;
	}

	public String getUpdateExceptionError() {
		return updateExceptionError;
	}

	public void setUpdateExceptionError(String updateExceptionError) {
		this.updateExceptionError = updateExceptionError;
	}

	public String getDeleteExceptionError() {
		return deleteExceptionError;
	}

	public void setDeleteExceptionError(String deleteExceptionError) {
		this.deleteExceptionError = deleteExceptionError;
	}

}
