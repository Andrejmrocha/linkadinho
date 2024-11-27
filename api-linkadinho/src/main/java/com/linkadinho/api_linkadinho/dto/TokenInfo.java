package com.linkadinho.api_linkadinho.dto;

public record TokenInfo(String subject, String role, String organization, Long id,  Long expirationTime) { }
