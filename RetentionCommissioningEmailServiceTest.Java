 
 Test Method : 
 @Test
    public void test_populateMailObjectAndSend() {
        final String htmlContent = null;
        prepareMocks();

        retentionCommissioningEmailService.populateMailObjectAndSend(mockDeal(), htmlContent);

        final ArgumentCaptor<MailDTO> mailDTOCaptor = ArgumentCaptor.forClass(MailDTO.class);
        // retentionCommissioningEmailService.populateMailObjectAndSend(mockDeal(), htmlContent)
        Mockito.verify(mailService).sendMail(mailDTOCaptor.capture(), Matchers.any(MailQueue.class), Matchers.anyString());
        // (invoiceProductCaptor.capture());
        System.out.println(mailDTOCaptor.toString());
        assertEquals(TEST_EMAIL, mailDTOCaptor.getValue().getToList().get(0));
        // Assert.assertThat(invoiceProductCaptor.getValue().getEnddateValid(), is(today));

    }
    
    __Java Core class Method :__
    
     protected void populateMailObjectAndSend(Deal deal, final String htmlContent) {
        final List<String> toEmailAddressOfDealer = new ArrayList<>();

        final Dealer dealer = deal.getDealer();
        if (dealer.getMainDealer() != null) {
            toEmailAddressOfDealer.add(dealer.getMainDealer().getAddressData().getEmail());
        } else {
            toEmailAddressOfDealer.add(dealer.getAddressData().getEmail());
        }

        final String offerteNumber = deal.getDealer().getCode() + "." + deal.getSerialNumber() + "." + deal.getVersionNumber() + "." + deal.getOfferNumber();
        final String subject = "vergoedingenoverzicht offerte " + offerteNumber;

        final MailQueue mailQueueObjectForDeal = MailUtility.createMailQueueObjectForDeal(deal);
        final MailDTO mailDTO = MailDTO.builder().toList(toEmailAddressOfDealer).from(fromEmailAddressForRetDealConfirmation).subject(subject).body(htmlContent).build();

        mailService.sendMail(mailDTO, mailQueueObjectForDeal, fromEmailAddressForRetDealConfirmation);
        LOG.info("Successfully sent retention commissioning mail for dealId: " + deal.getId());
    }

    
    