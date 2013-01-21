#!/usr/bin/env python

import logging
import MySQLdb
import pdb
import string
from random import choice

log = logging.getLogger('informea.odata.fixture')

class DecisionFixture(object):
	def __init__(self, cur, options):
		self.cur = cur
		self.options = options

	def run(self, cur, options):
		for idx in range(1, 5000):
			self.make_decision(cur, options, idx);

	def make_decision(self, cur, options, idx):
		# Insert into informea_decisions table
		link = 'http://whc.org/xxx'
		_type = choice(['decision', 'recommendation'])
		status = choice(['active', 'retired', 'ammended'])
		number = 'Decision #%4s' % idx
		treaty = 'whc'
		published = '2011-10-10 00:00:00'
		updated = published
		meetingTitle = 'COP %s' % idx
		sql = """INSERT INTO `informea_decisions` (`id`, `link`, `type`, `status`, `number`, `treaty`, `published`, `updated`, `meetingId`, `meetingTitle`, `meetingUrl`)
VALUE   ('%s', '%s', '%s', '%s',   '%s',   '%s',   '%s',      '%s',    NULL,      '%s',         NULL);\n""" % (idx, link, _type, status, number, treaty, published, updated, meetingTitle)
		if options.verbose:
			print sql
		cur.execute(sql)

		# Insert into informea_decisions_title table
		for language in ['en', 'fr']:
			sql = """INSERT INTO `informea_decisions_title` (`decision_id`, `language`, `title` ) VALUE ('%s', '%s', '%s');""" % (idx, language, '%s title for #%s' % (language, idx) );
			if options.verbose:
				print sql
		cur.execute(sql)


def parse_options():
	import optparse
	parser = optparse.OptionParser()
	parser.add_option('-v', '--verbose', action='store_true', dest='verbose', default=False)
	parser.add_option('-c', '--commit', action='store_true', dest='commit', default=False)
	options, args = parser.parse_args()
	return options

if __name__ == '__main__':
	options = parse_options()
	log_level = logging.DEBUG if options.verbose else logging.INFO
	logging.basicConfig(level=log_level)

	conn = MySQLdb.connect(db='odata_api', user='root', passwd='root', use_unicode=True)
	cur = conn.cursor()
	ob = DecisionFixture(cur, options)
	ob.run(cur, options)

	if options.commit:
		conn.commit()
	else:
		conn.rollback()

	conn.close()
	log.info('Done.')
