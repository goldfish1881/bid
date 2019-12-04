/** 
* Files can be saved in file system
*
@return {@code message} result of upload proccess
*/
@PreAuthorize("hasAuthority('FREELANCER')")
@PostMapping("/{id}/files")
public ResponseEntity<?> uploadBidAttachedFiles(@PathVariable("id") Bid bid, @AuthenticationPrincipal User user,
@RequestParam(name = "files") MultipartFile... files) {
Map<String, Object> message = new HashMap<>();
HttpStatus status;
if (bid == null) {
status = HttpStatus.NOT_FOUND;
message.put("error", "Bid not exits ");
} else if (!bid.getFreelancer().getUser().getId().equals(user.getId())) {
status = HttpStatus.UNAUTHORIZED;
} else {
try {
if (bidService.saveFiles(bid, files)) {
status = HttpStatus.OK;
} else {
status = HttpStatus.INTERNAL_SERVER_ERROR;
}
} catch (Exception e) {
status = HttpStatus.BAD_REQUEST;
message.put("message", e.getMessage());
}

}
return new ResponseEntity<>(message, status);
}