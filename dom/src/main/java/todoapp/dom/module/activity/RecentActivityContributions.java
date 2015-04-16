/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package todoapp.dom.module.activity;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;

import org.isisaddons.module.audit.dom.AuditingServiceRepository;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.publishing.dom.PublishingServiceRepository;
import org.isisaddons.module.sessionlogger.dom.SessionLogEntry;


@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class RecentActivityContributions extends AbstractService {

    /**
     * Depending on which services are available, returns either a list of {@link org.isisaddons.module.command.dom.CommandJdo command}s that have
     * caused a change in the domain object or a list of {@link org.isisaddons.module.audit.dom.AuditEntry audit entries} capturing the 'effect'
     * of that change.
     *
     * <p>
     * If {@link org.isisaddons.module.command.dom.CommandJdo command}s are returned, then the corresponding {@link org.isisaddons.module.audit.dom.AuditEntry audit entries} are
     * available from each command.
     */
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence="30")
    public List<? extends DomainChangeJdoAbstract> recentActivity (
            final Object targetDomainObject,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="From")
            final LocalDate from,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="To")
            final LocalDate to) {
        final Bookmark targetBookmark = bookmarkService.bookmarkFor(targetDomainObject);
        final List<DomainChangeJdoAbstract> changes = Lists.newArrayList();
        if(commandServiceRepository != null) {
            changes.addAll(commandServiceRepository.findByTargetAndFromAndTo(targetBookmark, from, to));
        } 
        if(publishingServiceRepository != null) {
            changes.addAll(publishingServiceRepository.findByTargetAndFromAndTo(targetBookmark, from, to));
        }
        changes.addAll(auditingServiceRepository.findByTargetAndFromAndTo(targetBookmark, from, to));
        Collections.sort(changes, DomainChangeJdoAbstract.compareByTimestampDescThenType());
        return changes;
    }
    /**
     * Hide for commands, audit entries, published events, session log entries, and for {@link org.apache.isis.applib.ViewModel}s.
     */
    public boolean hideRecentActivity(final Object targetDomainObject, final LocalDate from, final LocalDate to) {
        return  getContainer().isViewModel(targetDomainObject) ||
                targetDomainObject instanceof HasTransactionId ||
                targetDomainObject instanceof SessionLogEntry ||
                auditingServiceRepository == null ||
                bookmarkService == null;
    }
    public LocalDate default1RecentActivity() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default2RecentActivity() {
        return clockService.now();
    }

    
    // //////////////////////////////////////

    
    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;
    
    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;
    
    @javax.inject.Inject
    private PublishingServiceRepository publishingServiceRepository;
    
    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private ClockService clockService;
    
}

