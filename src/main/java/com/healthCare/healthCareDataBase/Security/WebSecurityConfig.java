package com.healthCare.healthCareDataBase.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.healthCare.healthCareDataBase.Security.jwt.AuthEntryPointJwt;
import com.healthCare.healthCareDataBase.Security.jwt.AuthTokenFilter;
import com.healthCare.healthCareDataBase.Security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**",
					"/api/test/**",
					"/api/user/existsByUsername/**",
					"/api/speciality/all",
					"/api/doctor/getApprovedDoctorsBySpecialityIdAndCity",
                    "/api/doctor/getDoctorAppointmentInfoByDoctorId/**",
                    "/api/appointment/appointmentsCountByDoctorIdAndDate",
                    "/api/image/get/**",
                    "/api/appointment/getAppointmentNumberByDoctorIdAndDate",
                    "/api/doctor/getDoctorInfoByDoctorId/**",
                    "/api/image/checkIfDocumentExist",
                    "/api/uploadExcelFile/import/**",
                    "/api/medicamentstock/get/**",
                    "/api/doctor/getTopRatedDoctor",
                    "/api/patient/getUserFullNameById/**",
                    "/api/user/getUserFullNameById/**",
                    "/api/pharmacy/findPharmacyByPrescriptonMedicamentAndGeoLocation",
                    "/api/mail/send",
                    "/api/user/checkVerifacationCode",
                    "/api/user/updateUserStatusByEmail",
                    "/api/user/updateUserPasswordByEmail",
                    "/api/validation/checkIfUserValidated/**",
                    "/api/socket/**",
                    "/api/pharmacy/getPharmacyInfoById/**").permitAll()
			.antMatchers("/api/admin/**").hasAuthority("ADMIN_ROLE")
			.antMatchers("/api/doctor/**").hasAnyAuthority("DOCTOR_ROLE","ADMIN_ROLE")
			.antMatchers("/api/patient/**").hasAnyAuthority("PATIENT_ROLE","ADMIN_ROLE")
			.antMatchers("/api/pharmacy/**").hasAnyAuthority("PHARMACIST_ROLE","ADMIN_ROLE")
			.antMatchers("/api/validation/**").hasAuthority("ADMIN_ROLE")
			.antMatchers("/api/image/upload").hasAnyAuthority("DOCTOR_ROLE","PATIENT_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/image/**").hasAuthority("ADMIN_ROLE")
			.antMatchers("/api/medicalProfile/add").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/medicalProfileDisease/getPateintMedicalProfileDiseasesByMedicalProfileId").hasAnyAuthority("DOCTOR_ROLE","PATIENT_ROLE")
			.antMatchers("/api/medicalProfileDisease/getPateintMedicalProfileDiseasesNumberByMedicalProfileId/**").hasAnyAuthority("DOCTOR_ROLE","PATIENT_ROLE")
			.antMatchers("/api/medicalProfile/getPatientMedicalProfileByMedicalProfileId/**").hasAnyAuthority("DOCTOR_ROLE","PATIENT_ROLE")
			.antMatchers("/api/appointment/add").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/appointment/getPatientAppointmentByPatientId").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/appointment/getAppointmentByDoctorIdAndDate").hasAnyAuthority("DOCTOR_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/appointment/updateAppointmentDateById").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/appointment/deleteAppointmentById/**").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/prescription/add").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/medicament/getMedicamentsByFirstLetters/**").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/prescriptionMedicament/add").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/prescription/deleteById").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/prescription/getPrescriptionByDoctorIdPatientIdAndDate").hasAnyAuthority("DOCTOR_ROLE","PATIENT_ROLE")
			.antMatchers("/api/medicalProfileDisease/getDiagnoseByMedicalProfileIdDoctorIdAndDate").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/medicalProfileDisease/add").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/medicalProfileDisease/deleteDiagnoseByMedicalProfileIdDoctorIdAndDate").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/appointment/changeAppointmentStatusById").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/prescriptionMedicament/getPrescriptionsByPatientIdAndPrescriptionStatus").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/prescriptionMedicament/getMedicamentsByPrescriptionId/**").hasAnyAuthority("PATIENT_ROLE","PHARMACIST_ROLE")
			.antMatchers("/api/pharmacy/getPharmacyInfoFromSecureLogin").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/medicamentstock/getStockNumberByPharmacyId/**").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/medicamentstock/deleteByPharmacyId/**").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/medicamentstock/deleteByMedicamentStockId/**").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/medicamentstock/searchMedByNameAndPharmacyId").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/question/add").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/question/getAll").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE")
			.antMatchers("/api/comment/getAll").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE")
			.antMatchers("/api/comment/add").hasAnyAuthority("DOCTOR_ROLE","PHARMACIST_ROLE")
			.antMatchers("/api/point/add").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE")
			.antMatchers("/api/point/deleteById/**").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE")
			.antMatchers("/api/notificaiotn/add").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/notificaiotn/getAll").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/notificaiotn/changeUnreadNotification").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/notificaiotn/deleteNotificationById").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/conversation/add").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/conversation/updateConversationStatusById").hasAnyAuthority("DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/conversation/getConversationByUserId").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/message/add").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/conversation/getConversationByid/**").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/message/getMessagesByConversationId").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/message/readConversationById").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/notification/sendNotificationWithSocket").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/user/searchUsersByName").hasAnyAuthority("PATIENT_ROLE","DOCTOR_ROLE","PHARMACIST_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/appointment/getAppointmentByDoctorIdAndPatientId").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/question/getQuestionsByUserId").hasAnyAuthority("PATIENT_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/appointment/delayAppointmentByAppId").hasAnyAuthority("DOCTOR_ROLE","SECRETARY_ROLE")
			.antMatchers("/api/pharmacy/getTodayPrescriptionNumberById/**").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/pharmacy/getPharmacyPrescriptionsById").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/pharmacy/searchPharamacyPrescriptionByPatientName").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/prescription/confirmPrescriptionById").hasAuthority("PHARMACIST_ROLE")
			.antMatchers("/api/doctor/addSecretary").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/doctor/sendSeeSecretaryWorkRequest").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/doctor/createSecretaryAccount").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/secretary/getSecretaryInfoBySecureLogin").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/updateSecretaryInfoBySecureLogin").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/updatePasswordBySecureLogin").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/getSecretaryWorkById/**").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/doctor/getMySecretaries").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/doctor/getSecretaryWorkById").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/doctor/checkSecretaryCode").hasAuthority("DOCTOR_ROLE")
			.antMatchers("/api/secretary/acceptDoctorAddRequest").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/refuseDoctorAddRequest").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/getUncofirmedApp").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/getAppointmentInfoById").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/confirmAppointmentById").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/refuseAppointmentById").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/secretary/getNextRequestByAppId").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/appointment/getAppointmentByDoctorIdAndDateForSec").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/appointment/getDoctorCurrentPatient").hasAuthority("SECRETARY_ROLE")
			.antMatchers("/api/patient/getMyDoctors").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/patient/getMySecretaries").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/patient/getMyPharmacies").hasAuthority("PATIENT_ROLE")
			.antMatchers("/api/rate/add").hasAuthority("PATIENT_ROLE")
			.anyRequest().authenticated();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
} 
