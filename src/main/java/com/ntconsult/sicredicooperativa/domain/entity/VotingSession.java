package com.ntconsult.sicredicooperativa.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntconsult.sicredicooperativa.domain.enums.VotingSessionStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author vanderson
 * 
 * Classe que representa a entidade Sessão de Votação com as informações de:
 * 
 *    Código da sessão (considera-se que deve conter exatamente 6 caracteres)
 *    Pauta associada (considera-se que uma Sessão pode estar associada à apenas 1 Pauta e uma Pauta deve estar associada a apenas uma Sessão), 
 *    Data (timestamp) de abertura da sessão 
 *    Prazo de fechamento em minutos (1 minuto por default)
 *    Status da sessão (ABERTA ou FECHADA). O status é determinado como ABERTO na criação da sessão e pode ser atualizado quando é requisitado o fechamento da sessão ou quando há tentativa de voto para uma sessão com prazo expirado 
 *
 */
@Getter
@Setter
@Entity
@Table(name = "VOTING_SESSION")
public class VotingSession implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ID_VOTING_SESSION")
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_AGENDA")
	private Agenda agenda;
	
	@Column(name = "OPEN_DATE", columnDefinition="DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openDate;
	
	@Column(name="CLOSING_DEADLINE")
	private Integer closingDeadlineInMinutes;
	
	@Column(name="CODE", length = 6)
	private String sessionCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS", length = 10)
	private VotingSessionStatusEnum votingSessionStatus;
	
	@JsonIgnore
	@OneToMany(mappedBy="votingSession")
	private List<Vote> votes;
	
	@Column(name="YES_TOTAL_VOTES")
	private Integer yesTotalVotes;
	
	@Column(name="NO_TOTAL_VOTES")
	private Integer noTotalVotes;
	
	public VotingSession() {
		super();
	}

	public VotingSession(Agenda agenda, Date openDate, Integer closingDeadlineInMinutes, String sessionCode) {
		super();
		this.agenda = agenda;
		this.openDate = openDate;
		this.closingDeadlineInMinutes = closingDeadlineInMinutes == null ? 1 : closingDeadlineInMinutes;
		this.sessionCode = sessionCode;
		this.votingSessionStatus = VotingSessionStatusEnum.OPEN;
	}
}