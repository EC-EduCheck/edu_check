export function getNthSegment(pathname, n) {
  const segments = pathname.split('/').filter(Boolean);
  return segments.length ? segments[n] : '';
}
