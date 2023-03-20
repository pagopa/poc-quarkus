import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

import { createOrganization, deleteOrganization } from "./modules/orgsEnrollment_client.js";

export let options = JSON.parse(open(__ENV.TEST_TYPE));

// read configuration
// note: SharedArray can currently only be constructed inside init code
// according to https://k6.io/docs/javascript-api/k6-data/sharedarray
const varsArray = new SharedArray('vars', function() {
	return JSON.parse(open(`./${__ENV.VARS}`)).environment;
});
// workaround to use shared array (only array should be used)
const vars = varsArray[0];
const orgsEnrollmentHost = `${vars.orgsEnrollmentHost}`;
const numberOfEventsToPreload = `${vars.numberOfEventsToPreload}`;

const accountPrimaryKey = `${__ENV.API_SUBSCRIPTION_KEY}`;

export function setup() {
}

function precondition() {
	// no pre conditions
}

function postcondition(organizationFiscalCode) {

	// Delete the newly created organization.
	let tag = {
		gpdMethod: "DeleteOrganization",
	};

	const params = {
   	    headers: {
   			'Content-Type': 'application/json'
    	},
   	};

	const r = deleteOrganization(orgsEnrollmentHost, organizationFiscalCode, params);

	console.log("Delete organization " + organizationFiscalCode + " " + r.status);

	check(r, {
		"DeleteOrganization status is 200": (_r) => r.status === 200,
	}, tag);
}

export default function(data) {


	// Create an organization
	let tag = {
		orgsEnrollmentMethod: "CreateOrganization",
	};

	const params = {
		headers: {
			'Content-Type': 'application/json'
		},
	};

    let mockOrganizationFiscalCode = 'mockOrganizationFiscalCode';
	console.log(orgsEnrollmentHost + 'organization/' + mockOrganizationFiscalCode);

	const response = createOrganization(orgsEnrollmentHost, mockOrganizationFiscalCode, params);

	console.log(`CreateOrganization ... ${response.status}`);

	check(response, {"CreateOrganization status is 201": (res) => (res.status === 201)}, tag);

	postcondition(mockOrganizationFiscalCode, params);
}
