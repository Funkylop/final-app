package com.hramyko.finalapp.entity;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class ConfirmationToken {

    private String confirmationToken;
    private Date createdAt;
    private TokenType tokenType;
    private String email;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String email, TokenType tokenType) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.email = email;
        this.tokenType = tokenType;
    }

    public ConfirmationToken(String confirmationToken, String email, Date date, TokenType tokenType) {
        this.confirmationToken = confirmationToken;
        this.email = email;
        this.createdAt = date;
        this.tokenType = tokenType;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationToken that = (ConfirmationToken) o;
        return confirmationToken.equals(that.confirmationToken) && Objects.equals(createdAt, that.createdAt) && tokenType == that.tokenType && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmationToken, createdAt, tokenType, email);
    }

    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "confirmationToken='" + confirmationToken + '\'' +
                ", createdAt=" + createdAt +
                ", tokenType=" + tokenType +
                ", email='" + email + '\'' +
                '}';
    }
}
