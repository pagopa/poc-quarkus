export function randomOrg(length, charset) {
  let res = '';
  while (length--) res += charset[(Math.random() * charset.length) | 0];
  return res;
}

