import http from 'k6/http';

export function getOrganization(orgsEnrollmentHost, organizationFiscalCode, params) {
    return http.get(orgsEnrollmentHost+`organizations/${organizationFiscalCode}`, params)
}

export function getOrganizations(orgsEnrollmentHost, params) {
    return http.get(orgsEnrollmentHost+`organizations/`, params)
}

export function createOrganization(orgsEnrollmentHost, organizationFiscalCode, params) {
    return http.post(orgsEnrollmentHost+`organizations/${organizationFiscalCode}`, params)
}

export function deleteOrganization(orgsEnrollmentHost, organizationFiscalCode, params) {
    return http.del(orgsEnrollmentHost+`organizations/${organizationFiscalCode}`, params)
}