package com.hyoseok.kotlinjpa.service.exception

class NotFoundMemberException(errorMessage: ErrorMessage) : RuntimeException(errorMessage.str)