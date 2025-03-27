export function getLastSegment(pathname) {
  const segments = pathname.split('/').filter(Boolean);
  return segments.length ? segments[segments.length - 1] : '';
}
